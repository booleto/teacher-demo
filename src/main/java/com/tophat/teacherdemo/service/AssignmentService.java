package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.AssignmentCreateRequest;
import com.tophat.teacherdemo.controller.vo.AssignmentPublicView;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing assignments.
 * <p>
 * Provides CRUD operations and handles the assignment lifecycle, including managing assignees and
 * synchronizing assignment status based on submissions.
 */
public interface AssignmentService {

    /**
     * Retrieves an assignment by its ID.
     *
     * @param id the ID of the assignment.
     * @return an {@link Optional} containing the assignment if found, or empty if not found.
     */
    Optional<Assignment> getAssignment(ObjectId id);

    /**
     * Retrieves the public view of an assignment by its ID.
     * <p>
     * This view may hide sensitive or internal information intended only for assignees or instructors.
     *
     * @param id the ID of the assignment.
     * @return an {@link Optional} containing the assignment public view if found, or empty if not found.
     */
    Optional<AssignmentPublicView> getAssignmentPublicView(ObjectId id);

    /**
     * Creates a new assignment.
     *
     * @param assignment the assignment creation request containing details of the new assignment.
     * @return the created {@link Assignment}.
     */
    Assignment createAssignment(AssignmentCreateRequest assignment);

    /**
     * Searches for assignments using a query string.
     *
     * @param query    the search query, which can match titles, descriptions, or other metadata.
     * @param pageable pagination and sorting information.
     * @return a {@link Page} of assignments matching the query.
     */
    Page<Assignment> searchAssignment(String query, Pageable pageable);

    /**
     * Updates an existing assignment.
     *
     * @param id                 the ID of the assignment to update.
     * @param assignmentRequest  the updated assignment details.
     * @return an {@link Optional} containing the updated assignment if the update was successful, or empty if not found.
     */
    Optional<Assignment> updateAssignment(ObjectId id, AssignmentCreateRequest assignmentRequest);

    /**
     * Adds a list of students as assignees to an assignment.
     *
     * @param assignmentId the ID of the assignment.
     * @param studentIds   the list of student IDs to add.
     * @return an {@link Optional} containing the updated assignment if found, or empty if not found.
     */
    Optional<Assignment> addAssignees(ObjectId assignmentId, List<ObjectId> studentIds);

    /**
     * Removes a list of students from the assignees of an assignment.
     *
     * @param assignmentId the ID of the assignment.
     * @param studentIds   the list of student IDs to remove.
     * @return an {@link Optional} containing the updated assignment if found, or empty if not found.
     */
    Optional<Assignment> removeAssignees(ObjectId assignmentId, List<ObjectId> studentIds);

    /**
     * Synchronizes the status of an assignment based on a given submission.
     * <p>
     * This method updates the assignment's status or related metrics according to the provided submission state.
     *
     * @param submission the submission to use for status synchronization.
     */
    void syncSubmissionStatus(Submission submission);

    /**
     * Deletes an assignment by its ID.
     *
     * @param id the ID of the assignment to delete.
     */
    void deleteAssignment(ObjectId id);
}
