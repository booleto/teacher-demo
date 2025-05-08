package com.tophat.teacherdemo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultipleChoiceAnswer.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = KeywordAnswer.class, name = "KEYWORD"),
        @JsonSubTypes.Type(value = SortingAnswer.class, name = "SORTING")
})
public interface Answer {
    boolean isEquals(Answer answer);
}