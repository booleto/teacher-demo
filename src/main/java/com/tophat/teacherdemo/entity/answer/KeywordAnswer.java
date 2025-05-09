package com.tophat.teacherdemo.entity.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("KEYWORD")
@Data
@AllArgsConstructor
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