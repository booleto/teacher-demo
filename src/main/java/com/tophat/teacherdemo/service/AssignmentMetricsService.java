package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.AssignmentMetricsSummary;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Service interface for aggregation metrics of assignments
 */
public interface AssignmentMetricsService {

    /**
     * Summarize important statistics of a given assignment.
     *
     * @param assignmentId The unique ID of the assignment
     * @return an {@link Optional} containing summary metrics of the assignment, or an empty if no assignments with matching ID found
     */
    Optional<AssignmentMetricsSummary> summarize(ObjectId assignmentId);
}
