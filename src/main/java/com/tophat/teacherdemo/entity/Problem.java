package com.tophat.teacherdemo.entity;

import com.tophat.teacherdemo.entity.answer.Answer;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Document("Problem")
@Data
@Builder
public class Problem<T extends Answer> implements Serializable {
    @Serial
    private static final long serialVersionUID = -1707L;

    @Id
    private ObjectId id;
    @TextIndexed
    private String title;
    @TextIndexed
    private String description;
    @TextIndexed
    private String questionText;
    private Type type;
    private T correctAnswer;
    private List<String> choices;
    private Map<String, Object> metadata;

    public enum Type {
        MULTIPLE_CHOICE, SORTING, KEYWORD
    }

    public boolean isAnswerCorrect(T answer) {
        return answer.isEquals(correctAnswer);
    }
}
