package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.AssignmentMetricsSummary;
import com.tophat.teacherdemo.controller.vo.ProblemAccuracy;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.repository.SubmissionRepository;
import com.tophat.teacherdemo.service.AssignmentMetricsService;
import com.tophat.teacherdemo.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentMetricsServiceImpl implements AssignmentMetricsService {
    private final AssignmentService assignmentService;
    private final SubmissionRepository submissionRepository;

    public Optional<AssignmentMetricsSummary> summarize(ObjectId assignmentId) {
        Optional<Assignment> foundAssignment = assignmentService.getAssignment(assignmentId);
        if (foundAssignment.isEmpty()) return Optional.empty();
        Assignment assignment = foundAssignment.get();

        List<ProblemAccuracy> avgAccuracyPerProblem = submissionRepository.averageGradeByProblem(assignmentId);

        return Optional.of(
                AssignmentMetricsSummary.builder()
                        .assignmentId(assignment.getId())
                        .title(assignment.getTitle())
                        .description(assignment.getDescription())
                        .submissionDeadline(assignment.getSubmissionDeadline())
                        .averageGrade(submissionRepository.averageGradeByAssignmentId(assignmentId))
                        .topGrade(submissionRepository.topGradeByAssignmentId(assignmentId))
                        .botGrade(submissionRepository.botGradeByAssignmentId(assignmentId))
                        .maxGrade((float) avgAccuracyPerProblem.size())
                        .accuracyPerProblem(avgAccuracyPerProblem.stream().collect(Collectors.toMap(
                                ProblemAccuracy::getId,
                                ProblemAccuracy::getAccuracy
                        )))
                .build()
        );
    }
}
