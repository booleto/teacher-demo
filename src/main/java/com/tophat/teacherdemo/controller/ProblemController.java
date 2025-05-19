package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.ProblemCreateRequest;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.exception.ResourceNotFoundException;
import com.tophat.teacherdemo.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/problem")
@Validated
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    // TODO: Restrict API access to prevent answer leaks
    @GetMapping("/{id}")
    public ResponseEntity<Problem<Answer>> getProblem(@PathVariable ObjectId id) {
        Optional<Problem<Answer>> foundAnswer = problemService.findProblemById(id);
        return foundAnswer.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Problem %s not found", id.toString())
                ));
    }

    @GetMapping
    public ResponseEntity<Page<Problem<Answer>>> searchProblems(@RequestParam @Pattern(regexp = "^[\\w\\s.,!?()]+$", message = "query must not contain special characters") String query,
                                                                @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(problemService.searchProblem(query, pageable));
    }

    @PostMapping
    public ResponseEntity<Problem<Answer>> createProblem(@RequestBody @Valid ProblemCreateRequest problemReq) {
        return ResponseEntity.ok(problemService.createProblem(problemReq));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Problem<Answer>> updateProblem(@RequestBody @Valid ProblemCreateRequest problemReq, @PathVariable ObjectId id) {
        Optional<Problem<Answer>> updatedAnswer = problemService.updateProblem(id, problemReq);
        return updatedAnswer.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Problem %s not found", id.toString())
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Problem<Answer>> deleteProblem(@PathVariable ObjectId id) {
        problemService.deleteProblem(id);
        return ResponseEntity.ok().build();
    }
}
