package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.ProblemCreateRequest;
import com.tophat.teacherdemo.entity.*;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.repository.ProblemRepository;
import com.tophat.teacherdemo.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    @Override
    public boolean existsProblemById(ObjectId id) {
        return problemRepository.existsById(id);
    }


    @Override
    @Cacheable(value = "problem-cache", key = "#id", unless = "#result == null")
    public Optional<Problem<Answer>> findProblemById(ObjectId id) {
        return problemRepository.findById(id);
    }


    public Page<Problem<Answer>> searchProblem(String query, Pageable pageable) {
        return problemRepository.findAllBy(query, pageable);
    }


    @Override
    @CachePut(value = "problem-cache", key = "#result.id", unless = "#result == null")
    public Problem<Answer> createProblem(ProblemCreateRequest problemRequest) {
        Problem<Answer> problem = Problem.builder()
                .title(problemRequest.getTitle())
                .description(problemRequest.getDescription())
                .questionText(problemRequest.getQuestionText())
                .type(problemRequest.getType())
                .correctAnswer(problemRequest.getCorrectAnswer().toAnswer())
                .choices(problemRequest.getChoices())
                .build();

        return problemRepository.save(problem);
    }


    @Override
    @CachePut(value = "problem-cache", key = "#id", unless = "#result == null")
    public Optional<Problem<Answer>> updateProblem(ObjectId id, ProblemCreateRequest problemRequest) {
        Optional<Problem<Answer>> problemSearch = problemRepository.findById(id);
        if (problemSearch.isEmpty()) return Optional.empty();
        Problem<Answer> problem = problemSearch.get();

        // Change question text
        Optional.ofNullable(problemRequest.getTitle()).ifPresent(problem::setTitle);
        Optional.ofNullable(problemRequest.getDescription()).ifPresent(problem::setDescription);
        Optional.ofNullable(problemRequest.getQuestionText()).ifPresent(problem::setQuestionText);
        Optional.ofNullable(problemRequest.getChoices()).ifPresent(problem::setChoices);

        // Change correct answer (same question type only)
        Optional.ofNullable(problemRequest.getCorrectAnswer()).ifPresent(answer -> {
            if (problemRequest.getType().equals(problem.getType())) {
                problem.setCorrectAnswer(answer.toAnswer());
            }
        });

        // Change question type
        Optional.ofNullable(problemRequest.getType()).ifPresent(type -> {
            if (Objects.isNull(problemRequest.getCorrectAnswer())) {
                throw new IllegalArgumentException("Changing Problem Type, but new Correct answer not found");
            }

            Answer correctAnswer = problemRequest.getCorrectAnswer().toAnswer();
            problem.setCorrectAnswer(correctAnswer);
        });

        return Optional.of(problemRepository.save(problem));
    }


    @Override
    @CacheEvict(value = "problem-cache", key = "#id")
    public void deleteProblem(ObjectId id) {
        problemRepository.deleteById(id);
    }
}