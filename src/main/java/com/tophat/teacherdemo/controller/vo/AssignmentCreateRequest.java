package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.entity.Assignment;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentCreateRequest {
    @NotNull(message = "title must not be null")
    @Size(max = 200, message = "title is too long")
    private String title;

    @NotNull(message = "description must not be null")
    @Size(max = 1000, message = "description is too long")
    private String description;

    @Future(message = "submission deadline must be in the future")
    private Instant submissionDeadline;

    private Assignment.Status status = Assignment.Status.DRAFT;
    private Map<String, Object> metadata;

    private List<ObjectId> assignees;
    private List<ObjectId> problemIds;
}
