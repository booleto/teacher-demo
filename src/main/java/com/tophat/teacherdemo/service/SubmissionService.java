package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.SubmissionRequest;
import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Interface for managing submissions.
 */
public interface SubmissionService {
    /**
     * Gets a submission with matching id if it exists.
     *
     * @param id unique ID of the submission.
     * @return an {@link Optional} containing the {@link Submission}, or an empty if no submissions with matching ID exists.
     */
    Optional<Submission> getSubmissionById(ObjectId id);

    /**
     * Creates a draft submission.
     * <p>
     * Draft submissions can be edited, but their corresponding assignment will not be counted as completed.
     * To complete assignments, turn in submissions using {@link #turnInSubmission(ObjectId)}
     *
     * @param submission Submission request
     * @return a {@link Submission} after being created
     */
    Submission createDraftSubmission(SubmissionRequest submission);

    /**
     * Update existing draft submission and returns the edited object, if any exists.
     *
     * @param id unique ID of the submission to edit
     * @param submission Submission request, containing fields to change
     * @return an {@link Optional} containing the updated {@link Submission}, or an empty if no submission with matching found.
     */
    Optional<Submission> updateDraftSubmission(ObjectId id, SubmissionRequest submission);

    /**
     * Delete submission with matching id.
     *
     * @param id unique ID of the submission to delete
     */
    void deleteSubmission(ObjectId id);

    /**
     * Turn in the selected submission, process it, and lock it from being edited.
     *
     * @param id unique ID of the submission to submit.
     * @return an {@link Optional} containing the processed {@link Submission}, or an empty if no such submission exists
     */
    Optional<Submission> turnInSubmission(ObjectId id);
}
