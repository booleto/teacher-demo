package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.AssignStudentRequest;
import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.controller.vo.AssignmentMetricsSummary;
import com.tophat.teacherdemo.controller.vo.AssignmentPublicView;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.service.AssignmentMetricsService;
import com.tophat.teacherdemo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/assignment")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentMetricsService assignmentMetricsService;

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable ObjectId id) {
        Optional<Assignment> foundAssignment = assignmentService.getAssignment(id);
        return foundAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/display")
    public ResponseEntity<AssignmentPublicView> getAssignmentPublicView(@PathVariable ObjectId id) {
        Optional<AssignmentPublicView> foundAssignment = assignmentService.getAssignmentPublicView(id);
        return foundAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Assignment>> searchAssignments(@RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(assignmentService.searchAssignment(query, pageable));
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

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Assignment> addAssignees(@PathVariable ObjectId id, @RequestBody AssignStudentRequest request) {
        Optional<Assignment> updatedAssignment = assignmentService.addAssignees(id, request.getStudentIds());
        return updatedAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/unassign")
    public ResponseEntity<Assignment> removeAssignee(@PathVariable ObjectId id, @RequestBody AssignStudentRequest request) {
        Optional<Assignment> updatedAssignment = assignmentService.removeAssignees(id, request.getStudentIds());
        return updatedAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<AssignmentMetricsSummary> summarizeAssignment(@PathVariable ObjectId id) {
        Optional<AssignmentMetricsSummary> foundAssignment = assignmentMetricsService.summarize(id);
        return foundAssignment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
