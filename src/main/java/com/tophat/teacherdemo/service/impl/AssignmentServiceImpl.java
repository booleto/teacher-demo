package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.entity.Answer;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.repository.AssignmentRepository;
import com.tophat.teacherdemo.repository.ProblemRepository;
import com.tophat.teacherdemo.service.AssignmentService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final ProblemRepository problemRepository;

    @Override
    public Optional<Assignment> getAssignment(ObjectId id) {
        return assignmentRepository.findById(id);
    }

    @Override
    public Assignment createAssignment(AssignmentCreateRequest assignmentRequest) {
        Assignment assignment = Assignment.builder()
                .title(assignmentRequest.getTitle())
                .description(assignmentRequest.getDescription())
                .submissionDeadline(assignmentRequest.getSubmissionDeadline())
                .metadata(assignmentRequest.getMetadata())
                .status(assignmentRequest.getStatus())
                .build();

        updateProblemList(assignment, assignmentRequest.getProblemIds());
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> searchAssignment(Assignment assignment) {
        return assignmentRepository.findAll(Example.of(assignment));
    }

    @Override
    public Optional<Assignment> updateAssignment(ObjectId id, AssignmentCreateRequest assignmentRequest) {
        Optional<Assignment> assignmentSearch = assignmentRepository.findById(id);
        if (assignmentSearch.isEmpty()) return Optional.empty();
        Assignment assignment = assignmentSearch.get();

        // Change text
        Optional.ofNullable(assignmentRequest.getTitle()).ifPresent(assignment::setTitle);
        Optional.ofNullable(assignmentRequest.getDescription()).ifPresent(assignment::setDescription);
        Optional.ofNullable(assignmentRequest.getSubmissionDeadline()).ifPresent(assignment::setSubmissionDeadline);
        Optional.ofNullable(assignmentRequest.getStatus()).ifPresent(assignment::setStatus);
        Optional.ofNullable(assignmentRequest.getMetadata()).ifPresent(assignment::setMetadata);

        // Change problem list
        if (Objects.nonNull(assignmentRequest.getProblemIds())) {
            updateProblemList(assignment, assignmentRequest.getProblemIds());
        }

        return Optional.of(assignmentRepository.save(assignment));
    }


    @Override
    public void deleteAssignment(ObjectId id) {
        assignmentRepository.deleteById(id);
    }


    private void updateProblemList(Assignment assignment, List<ObjectId> problemIds) {
        List<Problem<Answer>> foundProblems = problemRepository.findAllById(problemIds);
        Set<ObjectId> foundIds = foundProblems.stream()
                .map(Problem::getId)
                .collect(Collectors.toSet());
        List<ObjectId> notFoundIds = problemIds.parallelStream()
                .filter(Predicate.not(foundIds::contains))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new IllegalArgumentException(String.format("Problem with id %s not found", notFoundIds));
        }

        assignment.setProblems(foundProblems);
    }
}
