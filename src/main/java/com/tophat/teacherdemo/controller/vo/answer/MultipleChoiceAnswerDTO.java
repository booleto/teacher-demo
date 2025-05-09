package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.MultipleChoiceAnswer;
import lombok.Data;

import java.util.Objects;

@JsonTypeName("MULTIPLE_CHOICE")
@Data
public class MultipleChoiceAnswerDTO implements AnswerDTO {
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
