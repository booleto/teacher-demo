package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.entity.Assignment;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {
    Optional<Assignment> getAssignment(ObjectId id);
    Assignment createAssignment(AssignmentCreateRequest assignment);
    List<Assignment> searchAssignment(Assignment assignment);
    Optional<Assignment> updateAssignment(ObjectId id, AssignmentCreateRequest assignmentRequest);
    void deleteAssignment(ObjectId id);
}
