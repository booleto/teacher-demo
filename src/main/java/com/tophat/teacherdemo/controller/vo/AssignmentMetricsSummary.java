package com.tophat.teacherdemo.controller.vo;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class AssignmentMetricsSummary {
    private ObjectId assignmentId;
    private String title;
    private String description;
    private Instant submissionDeadline;
    private Float averageGrade;
    private Float topGrade;
    private Float botGrade;
    private Float maxGrade;
    private Map<ObjectId, Float> accuracyPerProblem;
}
