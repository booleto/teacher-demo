package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.entity.Assignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentCreateRequest {
    private String title;
    private String description;
    private Instant submissionDeadline;
    private Assignment.Status status;
    private Map<String, Object> metadata;

    private List<ObjectId> assignees;
    private List<ObjectId> problemIds;
}
