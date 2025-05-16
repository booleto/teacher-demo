package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.ProblemCreateRequest;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Interface for managing problems (questions).
 */
public interface ProblemService {
    /**
     * Checks whether a problem with the specified ID exists.
     *
     * @param id the unique identifier of the problem.
     * @return {@code true} if the problem exists, {@code false} otherwise.
     */
    boolean existsProblemById(ObjectId id);

    /**
     * Retrieves a problem by its unique ID.
     *
     * @param id the unique identifier of the problem.
     * @return an {@link Optional} containing the problem if found, or empty if not found.
     */
    Optional<Problem<Answer>> findProblemById(ObjectId id);

    /**
     * Searches for problems based on a text query. Supports pagination.
     *
     * @param query    the search query, typically matching problem titles, content, or tags.
     * @param pageable the pagination and sorting information.
     * @return a {@link Page} of matching problems.
     */
    Page<Problem<Answer>> searchProblem(String query, Pageable pageable);

    /**
     * Creates a new problem in the system.
     *
     * @param problem the details of the problem to create.
     * @return the created problem with its generated ID and additional metadata.
     */
    Problem<Answer> createProblem(ProblemCreateRequest problem);

    /**
     * Updates an existing problem with the given ID using the provided details.
     *
     * @param id      the unique identifier of the problem to update.
     * @param problem the new details of the problem.
     * @return an {@link Optional} containing the updated problem if the problem exists,
     * or empty if the problem was not found.
     */
    Optional<Problem<Answer>> updateProblem(ObjectId id, ProblemCreateRequest problem);

    /**
     * Deletes the problem identified by the given ID.
     *
     * @param id the unique identifier of the problem to delete.
     */
    void deleteProblem(ObjectId id);
}
