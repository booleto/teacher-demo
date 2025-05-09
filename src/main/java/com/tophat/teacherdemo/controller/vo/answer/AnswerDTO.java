package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswerDTO.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = KeywordAnswerDTO.class, name = "KEYWORD"),
        @JsonSubTypes.Type(value = SortingAnswerDTO.class, name = "SORTING")
})
public interface AnswerDTO {
    Answer toAnswer();
    Problem.Type getType();
}
