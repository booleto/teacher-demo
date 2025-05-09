package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Submission;
import com.tophat.teacherdemo.repository.SubmissionRepository;
import com.tophat.teacherdemo.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;

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
    public Optional<Submission> turnInSubmission(ObjectId id) {
        Optional<Submission> submissionSearch = getSubmissionById(id);
        if (submissionSearch.isEmpty()) return Optional.empty();
        Submission submission = submissionSearch.get();

        if (!submission.getStatus().equals(Submission.Status.DRAFT)) {
            throw new IllegalArgumentException("Submission status is not DRAFT, cannot turn in");
        }

        submission.setStatus(Submission.Status.TURNED_IN);
        return Optional.of(submissionRepository.save(submission));
    }
}
