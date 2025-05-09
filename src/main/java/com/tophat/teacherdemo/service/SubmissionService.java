package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface SubmissionService {
    Optional<Submission> getSubmissionById(ObjectId id);
    Submission createDraftSubmission(SubmissionRequest submission);
    Optional<Submission> updateDraftSubmission(ObjectId id, SubmissionRequest submission);
    void deleteSubmission(ObjectId id);
    Optional<Submission> turnInSubmission(ObjectId id);
}
