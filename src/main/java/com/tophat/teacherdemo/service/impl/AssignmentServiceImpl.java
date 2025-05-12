package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.controller.vo.AssignmentPublicView;
import com.tophat.teacherdemo.entity.*;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.repository.AssignmentRepository;
import com.tophat.teacherdemo.repository.ProblemRepository;
import com.tophat.teacherdemo.service.AssignmentService;
import com.tophat.teacherdemo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final ProblemRepository problemRepository;
    private final StudentService studentService;

    @Override
    public Optional<Assignment> getAssignment(ObjectId id) {
        return assignmentRepository.findById(id);
    }

    @Override
    public Optional<AssignmentPublicView> getAssignmentPublicView(ObjectId id) {
        Optional<Assignment> assignmentSearch = assignmentRepository.findById(id);
        if (assignmentSearch.isEmpty()) return Optional.empty();
        Assignment assignment = assignmentSearch.get();

        assignment.getProblems().forEach(problem -> problem.setCorrectAnswer(null));
        AssignmentPublicView publicView = AssignmentPublicView.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .submissionDeadline(assignment.getSubmissionDeadline())
                .problems(assignment.getProblems())
                .build();
        return Optional.of(publicView);
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

        List<StudentSubmissionStatus> submissionStatuses = new HashSet<>(assignmentRequest.getAssignees())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(studentId -> {
                    Optional<Student> student = studentService.findStudentById(studentId);
                    if (student.isEmpty())
                        throw new IllegalArgumentException(String.format("Student with id %s not found" , studentId));
                    return student.get();
                })
                .map(student -> StudentSubmissionStatus.builder()
                        .studentId(student.getId())
                        .displayName(student.getDisplayName())
                        .submissionId(null)
                        .status(StudentSubmissionStatus.Status.UNSUBMITTED)
                        .build()
                ).toList();
        assignment.setSubmissionStatus(submissionStatuses);
        Assignment createdAssignment = assignmentRepository.save(assignment);

        List<ObjectId> validStudentList = submissionStatuses.stream()
                .map(StudentSubmissionStatus::getStudentId).toList();
        studentService.addPendingAssignment(validStudentList, createdAssignment);

        return createdAssignment;
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
    @Transactional
    public void deleteAssignment(ObjectId id) {
        Optional<Assignment> assignmentSearch = assignmentRepository.findById(id);
        if (assignmentSearch.isEmpty()) return;
        Assignment assignment = assignmentSearch.get();

        List<ObjectId> assignedStudents = assignment.getSubmissionStatus().parallelStream()
                .map(StudentSubmissionStatus::getStudentId)
                .toList();

        studentService.removePendingAssignment(assignedStudents, id);
        assignmentRepository.deleteById(id);
    }


    @Transactional
    public Optional<Assignment> addAssignees(ObjectId assignmentId, List<ObjectId> studentIds) {
        Optional<Assignment> foundAssignment = getAssignment(assignmentId);
        if (foundAssignment.isEmpty()) return Optional.empty();
        Assignment assignment = foundAssignment.get();

        Set<ObjectId> assignedStudents = new HashSet<>(
                assignment.getSubmissionStatus().stream()
                        .map(StudentSubmissionStatus::getStudentId)
                        .toList());

        List<StudentSubmissionStatus> newSubmissionStatuses = new HashSet<>(studentIds).parallelStream()
                .filter(Predicate.not(assignedStudents::contains))
                .map(id -> {
                    Optional<Student> student = studentService.findStudentById(id);
                    if (student.isEmpty()) throw new IllegalArgumentException(String.format("Student with id %s not found" , id));
                    return student.get();
                }).map(student -> StudentSubmissionStatus.builder()
                        .displayName(student.getDisplayName())
                        .studentId(student.getId())
                        .status(StudentSubmissionStatus.Status.UNSUBMITTED)
                        .build()
                ).toList();
        assignment.getSubmissionStatus().addAll(newSubmissionStatuses);

        studentService.addPendingAssignment(studentIds, assignment);
        return Optional.of(assignmentRepository.save(assignment));
    }


    @Transactional
    public Optional<Assignment> removeAssignees(ObjectId assignmentId, List<ObjectId> studentIds) {
        Optional<Assignment> foundAssignment = getAssignment(assignmentId);
        if (foundAssignment.isEmpty()) return Optional.empty();
        Assignment assignment = foundAssignment.get();

        if (studentIds.isEmpty()) return Optional.of(assignment);

        studentService.removePendingAssignment(studentIds, assignment.getId());
        assignment.getSubmissionStatus().removeIf(status -> studentIds.contains(status.getStudentId()));
        return Optional.of(assignmentRepository.save(assignment));
    }


    private void updateProblemList(Assignment assignment, List<ObjectId> problemIds) {
        if (Objects.isNull(problemIds)) return;

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
