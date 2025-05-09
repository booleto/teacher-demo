package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.Submission;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/submit")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    // TODO: Restrict API access to prevent answer leaks
    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmission(@PathVariable ObjectId id) {
        Optional<Submission> foundSubmission = submissionService.getSubmissionById(id);
        return foundSubmission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Submission> createDraftSubmission(@RequestBody SubmissionRequest submissionRequest) {
        return ResponseEntity.ok(submissionService.createDraftSubmission(submissionRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Submission> updateDraftSubmission(@RequestBody SubmissionRequest submissionRequest, @PathVariable ObjectId id) {
        Optional<Submission> updatedSubmission = submissionService.updateDraftSubmission(id, submissionRequest);
        return updatedSubmission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Problem<Answer>> deleteSubmission(@PathVariable ObjectId id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Submission> turnInSubmission(@PathVariable ObjectId id) {
        Optional<Submission> turnedIn = submissionService.turnInSubmission(id);
        return turnedIn.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
