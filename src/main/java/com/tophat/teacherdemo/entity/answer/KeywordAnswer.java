package com.tophat.teacherdemo.entity.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

import static com.tophat.teacherdemo.entity.answer.KeywordAnswer.ALIAS;

@Document
@TypeAlias(ALIAS)
@Data
@AllArgsConstructor
public class KeywordAnswer implements Answer, Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    public static final String ALIAS = "KEYWORD";
    private String keyword;

    @Override
    public boolean isEquals(Answer obj) {
        if (obj instanceof KeywordAnswer keywordAnswer) {
            return keyword.equals(keywordAnswer.getKeyword());
        }

        return false;
    }
}