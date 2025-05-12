package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Submission;
import com.tophat.teacherdemo.entity.SubmissionItem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.repository.SubmissionRepository;
import com.tophat.teacherdemo.service.AssignmentService;
import com.tophat.teacherdemo.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final AssignmentService assignmentService;

    @Override
    public Optional<Submission> getSubmissionById(ObjectId id) {
        return submissionRepository.findById(id);
    }

    @Override
    public Submission createDraftSubmission(SubmissionRequest submitRequest) {
        return submissionRepository.save(
                Submission.builder()
                        .studentId(submitRequest.getStudentId())
                        .assignmentId(submitRequest.getAssignmentId())
                        .metadata(submitRequest.getMetadata())
                        .submissionItems(submitRequest.getSubmissionItems())
                        .status(Submission.Status.DRAFT)
                        .build()
        );
    }


    @Override
    public Optional<Submission> updateDraftSubmission(ObjectId id, SubmissionRequest submitRequest) {
        Optional<Submission> submissionSearch = getSubmissionById(id);
        if (submissionSearch.isEmpty()) return Optional.empty();
        Submission submission = submissionSearch.get();

        if (!submission.getStatus().equals(Submission.Status.DRAFT)) {
            throw new IllegalArgumentException("Submission status is not DRAFT, editing is not allowed");
        }

        Optional.ofNullable(submitRequest.getMetadata()).ifPresent(submission::setMetadata);
        Optional.ofNullable(submitRequest.getSubmissionItems()).ifPresent(submission::setSubmissionItems);
        return Optional.of(submissionRepository.save(submission));
    }


    @Override
    public void deleteSubmission(ObjectId id) {
        submissionRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Optional<Submission> turnInSubmission(ObjectId id) {
        Optional<Submission> submissionSearch = getSubmissionById(id);
        if (submissionSearch.isEmpty()) return Optional.empty();
        Submission submission = submissionSearch.get();

        if (!submission.getStatus().equals(Submission.Status.DRAFT)) {
            throw new IllegalArgumentException("Submission status is not DRAFT, cannot turn in");
        }

        submission.setStatus(Submission.Status.TURNED_IN);
        gradeSubmission(submission);
        return Optional.of(submissionRepository.save(submission));
    }


    private void gradeSubmission(Submission submission) {
        Optional<Assignment> foundAssignment = assignmentService.getAssignment(submission.getAssignmentId());
        if (foundAssignment.isEmpty())
            throw new IllegalArgumentException(String.format("AssignmentId %s not found", submission.getAssignmentId()));
        Assignment assignment = foundAssignment.get();

        Map<ObjectId, Answer> studentAnswers = submission.getSubmissionItems().stream()
            .collect(Collectors.toMap(
                    SubmissionItem::getProblemId,
                    item -> item.getAnswer().toAnswer()
        ));

        AtomicInteger correctAnswerCount = new AtomicInteger(0);
        Map<ObjectId, Boolean> answerCheckResults = assignment.getProblems().stream()
                .map(problem -> {
                    Answer studentAnswer = studentAnswers.get(problem.getId());
                    if (Objects.isNull(studentAnswer)) return null;
                    Answer correctAnswer = problem.getCorrectAnswer();
                    boolean isStudentCorrect = studentAnswer.equals(correctAnswer);

                    if (isStudentCorrect) correctAnswerCount.incrementAndGet();
                    return Map.entry(problem.getId(), isStudentCorrect);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        submission.setGrade(correctAnswerCount.get());
        submission.getMetadata().put("grading_results", answerCheckResults);
    }
}
