package com.tophat.teacherdemo.entity.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

import static com.tophat.teacherdemo.entity.answer.MultipleChoiceAnswer.ALIAS;

/**
 * An {@link Answer} that indicates a choice for a multiple choice question.
 */
@Document
@TypeAlias(ALIAS)
@Data
@AllArgsConstructor
public class MultipleChoiceAnswer implements Answer, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ALIAS = "MULTIPLE_CHOICE";
    private int choice;

    @Override
    public boolean isEquals(Answer answer) {
        if (answer instanceof MultipleChoiceAnswer mcAnswer) {
            return choice == mcAnswer.getChoice();
        }

        return false;
    }
}
