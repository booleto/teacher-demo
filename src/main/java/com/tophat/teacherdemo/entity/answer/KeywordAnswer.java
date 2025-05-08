package com.tophat.teacherdemo.entity.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("KEYWORD")
@Data
public class KeywordAnswer implements Answer {
    private String keyword;

    @Override
    public boolean isEquals(Answer obj) {
        if (obj instanceof KeywordAnswer keywordAnswer) {
            return keyword.equals(keywordAnswer.getKeyword());
        }

        return false;
    }
}