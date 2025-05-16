package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.SortingAnswer;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

import static com.tophat.teacherdemo.controller.vo.answer.SortingAnswerDTO.JSON_TYPE;

/**
 * A DTO that describes a {@link SortingAnswer} entity.
 * Intended to be a representation of API input.
 */
@JsonTypeName(JSON_TYPE)
@Data
public class SortingAnswerDTO implements AnswerDTO {
    public static final String JSON_TYPE = "SORTING";

    @NotNull(message = "field 'orderedAnswers' is required in SORTING type answer")
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
