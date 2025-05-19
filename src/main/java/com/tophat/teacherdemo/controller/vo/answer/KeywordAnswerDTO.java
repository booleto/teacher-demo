package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.KeywordAnswer;
import com.tophat.teacherdemo.exception.InvalidAnswerException;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

import static com.tophat.teacherdemo.controller.vo.answer.KeywordAnswerDTO.JSON_TYPE;

/**
 * A DTO that describes a {@link KeywordAnswer} entity.
 * Intended to be a representation of API input.
 */
@JsonTypeName(JSON_TYPE)
@Data
public class KeywordAnswerDTO implements AnswerDTO {
    public static final String JSON_TYPE = "KEYWORD";

    @NotNull(message = "field 'keyword' is required in KEYWORD type answer")
    private String keyword;

    @Override
    public Answer toAnswer() {
        if (Objects.isNull(keyword)) {
            throw new InvalidAnswerException("Keyword answer data not found");
        }

        return new KeywordAnswer(keyword);
    }

    @Override
    public Problem.Type getType() {
        return Problem.Type.KEYWORD;
    }
}
