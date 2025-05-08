package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.ProblemCreateRequest;
import com.tophat.teacherdemo.entity.Answer;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.service.ProblemService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/problem")
@AllArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/{id}")
    public ResponseEntity<Problem<Answer>> getProblem(@PathVariable ObjectId id) {
        Optional<Problem<Answer>> foundAnswer = problemService.findProblemById(id);
        return foundAnswer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Problem<Answer>> createProblem(@RequestBody ProblemCreateRequest problemReq) {
        return ResponseEntity.ok(problemService.createProblem(problemReq));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Problem<Answer>> updateProblem(@RequestBody ProblemCreateRequest problemReq, @PathVariable ObjectId id) {
        Optional<Problem<Answer>> updatedAnswer = problemService.updateProblem(id, problemReq);
        return updatedAnswer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Problem<Answer>> deleteProblem(@PathVariable ObjectId id) {
        problemService.deleteProblem(id);
        return ResponseEntity.ok().build();
    }
}
