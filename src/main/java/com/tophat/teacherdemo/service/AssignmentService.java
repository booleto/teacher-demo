package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.controller.vo.AssignmentPublicView;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    Optional<Assignment> getAssignment(ObjectId id);
    Optional<AssignmentPublicView> getAssignmentPublicView(ObjectId id);
    Assignment createAssignment(AssignmentCreateRequest assignment);
    Page<Assignment> searchAssignment(String query, Pageable pageable);
    Optional<Assignment> updateAssignment(ObjectId id, AssignmentCreateRequest assignmentRequest);
    Optional<Assignment> addAssignees(ObjectId assignmentId, List<ObjectId> studentIds);
    Optional<Assignment> removeAssignees(ObjectId assignmentId, List<ObjectId> studentIds);
    void syncSubmissionStatus(Submission submission);
    void deleteAssignment(ObjectId id);
}
