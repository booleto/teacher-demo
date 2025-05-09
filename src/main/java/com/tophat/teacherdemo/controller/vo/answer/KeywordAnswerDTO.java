package com.tophat.teacherdemo.controller.vo.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.answer.KeywordAnswer;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;


@JsonTypeName("KEYWORD")
@Data
public class KeywordAnswerDTO implements AnswerDTO {
    private String keyword;

    @Override
    public Answer toAnswer() {
        if (Strings.isEmpty(keyword)) {
            throw new IllegalStateException("Keyword answer data not found");
        }

        return new KeywordAnswer(keyword);
    }

    @Override
    public Problem.Type getType() {
        return Problem.Type.KEYWORD;
    }
}
