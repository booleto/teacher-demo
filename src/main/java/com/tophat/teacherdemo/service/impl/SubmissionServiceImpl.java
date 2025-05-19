package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.SubmissionItemDTO;
import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Submission;
import com.tophat.teacherdemo.entity.SubmissionItem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.exception.InvalidSubmissionStateException;
import com.tophat.teacherdemo.exception.ResourceNotFoundException;
import com.tophat.teacherdemo.repository.SubmissionRepository;
import com.tophat.teacherdemo.service.AssignmentService;
import com.tophat.teacherdemo.service.StudentService;
import com.tophat.teacherdemo.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final StudentService studentService;

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
                        .submissionItems(submitRequest.getSubmissionItems().stream()
                                .map(SubmissionItemDTO::toEntity)
                                .toList()
                        )
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
            throw new InvalidSubmissionStateException("Submission status is not DRAFT, editing is not allowed", submission.getStatus().name());
        }

        Optional.ofNullable(submitRequest.getMetadata()).ifPresent(submission::setMetadata);
        Optional.ofNullable(submitRequest.getSubmissionItems()).ifPresent(items -> {
            List<SubmissionItem> newItemList = items.stream().map(SubmissionItemDTO::toEntity).toList();
            submission.setSubmissionItems(newItemList);
        });
        return Optional.of(submissionRepository.save(submission));
    }


    @Override
    public void deleteSubmission(ObjectId id) {
        submissionRepository.deleteByIdIfStatusEquals(id, Submission.Status.DRAFT);
    }


    @Override
    @Transactional
    public Optional<Submission> turnInSubmission(ObjectId id) {
        Optional<Submission> submissionSearch = getSubmissionById(id);
        if (submissionSearch.isEmpty()) return Optional.empty();
        Submission submission = submissionSearch.get();

        if (!submission.getStatus().equals(Submission.Status.DRAFT)) {
            throw new InvalidSubmissionStateException("Submission status is not DRAFT, cannot turn in", submission.getStatus().name());
        }

        submission.setStatus(Submission.Status.TURNED_IN);
        gradeSubmission(submission);

        assignmentService.syncSubmissionStatus(submission);
        studentService.removePendingAssignment(
                List.of(submission.getStudentId()), submission.getAssignmentId());

        return Optional.of(submissionRepository.save(submission));
    }


    private void gradeSubmission(Submission submission) {
        Optional<Assignment> foundAssignment = assignmentService.getAssignment(submission.getAssignmentId());
        if (foundAssignment.isEmpty())
            throw new ResourceNotFoundException(String.format("AssignmentId %s not found", submission.getAssignmentId()));
        Assignment assignment = foundAssignment.get();

        Map<ObjectId, Answer> studentAnswers = submission.getSubmissionItems().stream()
            .collect(Collectors.toMap(
                    SubmissionItem::getProblemId,
                    SubmissionItem::getAnswer
        ));

        AtomicInteger correctAnswerCount = new AtomicInteger(0);
        Map<ObjectId, Boolean> answerCheckResults = assignment.getProblems().stream()
                .map(problem -> {
                    Answer studentAnswer = studentAnswers.get(problem.getId());
                    if (Objects.isNull(studentAnswer))
                        return Map.entry(problem.getId(), false);

                    Answer correctAnswer = problem.getCorrectAnswer();
                    boolean isStudentCorrect = studentAnswer.equals(correctAnswer);

                    if (isStudentCorrect) correctAnswerCount.incrementAndGet();
                    return Map.entry(problem.getId(), isStudentCorrect);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        submission.setGrade(correctAnswerCount.get());
        submission.getMetadata().put("grading_results", answerCheckResults);
    }
}
