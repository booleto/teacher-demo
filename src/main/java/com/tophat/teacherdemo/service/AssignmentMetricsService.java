package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.AssignmentMetricsSummary;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface AssignmentMetricsService {
    Optional<AssignmentMetricsSummary> summarize(ObjectId assignmentId);
}
