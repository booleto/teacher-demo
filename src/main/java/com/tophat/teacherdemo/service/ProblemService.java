package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.ProblemCreateRequest;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProblemService {
    boolean existsProblemById(ObjectId id);
    Optional<Problem<Answer>> findProblemById(ObjectId id);
    Page<Problem<Answer>> searchProblem(String query, Pageable pageable);
    Problem<Answer> createProblem(ProblemCreateRequest problem);
    Optional<Problem<Answer>> updateProblem(ObjectId id, ProblemCreateRequest problem);
    void deleteProblem(ObjectId id);
}
