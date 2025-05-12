package com.tophat.teacherdemo.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubmissionRequest {
    private ObjectId studentId;
    private ObjectId assignmentId;
    private List<SubmissionItemDTO> submissionItems;
    private Map<String, Object> metadata;
}
