package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.SortingAnswer;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@JsonTypeName("SORTING")
@Data
public class SortingAnswerDTO implements AnswerDTO {
    private List<String> orderedAnswers;

    @Override
    public Answer toAnswer() {
        if (Objects.isNull(orderedAnswers)) {
            throw new IllegalStateException("Sorting answer data not found");
        }

        return new SortingAnswer(orderedAnswers);
    }

    @Override
    public Problem.Type getType() {
        return Problem.Type.SORTING;
    }
}
