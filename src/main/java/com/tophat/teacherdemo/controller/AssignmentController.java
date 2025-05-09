package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable ObjectId id) {
        Optional<Assignment> foundAssignment = assignmentService.getAssignment(id);
        return foundAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody AssignmentCreateRequest assignment) {
        return ResponseEntity.ok(assignmentService.createAssignment(assignment));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@RequestBody AssignmentCreateRequest assignment, @PathVariable ObjectId id) {
        Optional<Assignment> updatedAssignment = assignmentService.updateAssignment(id, assignment);
        return updatedAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable ObjectId id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok().build();
    }
}
