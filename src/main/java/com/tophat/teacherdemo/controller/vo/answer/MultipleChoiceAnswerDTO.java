package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.MultipleChoiceAnswer;
import lombok.Data;

import java.util.Objects;

import static com.tophat.teacherdemo.controller.vo.answer.MultipleChoiceAnswerDTO.JSON_TYPE;

/**
 * A DTO that describes a {@link MultipleChoiceAnswer} entity.
 * Intended to be a representation of API input.
 */
@JsonTypeName(JSON_TYPE)
@Data
public class MultipleChoiceAnswerDTO implements AnswerDTO {
    public static final String JSON_TYPE = "MULTIPLE_CHOICE";
    private Integer choice;

    @Override
    public Answer toAnswer() {
        if (Objects.isNull(choice)) {
            throw new IllegalStateException("Multiple choice answer data not found");
        }

        return new MultipleChoiceAnswer(choice);
    }

    @Override
    public Problem.Type getType() {
        return Problem.Type.MULTIPLE_CHOICE;
    }
}
