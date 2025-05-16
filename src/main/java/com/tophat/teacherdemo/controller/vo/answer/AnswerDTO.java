package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;

/**
 * Interface for DTOs that describes an {@link Answer}.
 * <p>
 * Intended to be deserialized from API inputs.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswerDTO.class, name = MultipleChoiceAnswerDTO.JSON_TYPE),
        @JsonSubTypes.Type(value = KeywordAnswerDTO.class, name = KeywordAnswerDTO.JSON_TYPE),
        @JsonSubTypes.Type(value = SortingAnswerDTO.class, name = SortingAnswerDTO.JSON_TYPE)
})
public interface AnswerDTO {

    /**
     * Convert this object to an {@link Answer} entity.
     * @return the converted {@link Answer}.
     */
    Answer toAnswer();

    /**
     * @return the type of {@link Problem} corresponding to this {@link Answer}
     */
    Problem.Type getType();
}
