package com.tophat.teacherdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmissionRequest {
    @NotNull(message = "studentId is required")
    private ObjectId studentId;

    @NotNull(message = "assignmentId is required")
    private ObjectId assignmentId;

    @NotNull(message = "submissionItems is required")
    private List<SubmissionItemDTO> submissionItems;

    private Map<String, Object> metadata;
}
