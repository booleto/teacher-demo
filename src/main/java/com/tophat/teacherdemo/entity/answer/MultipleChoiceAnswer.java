package com.tophat.teacherdemo.entity.answer;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("MULTIPLE_CHOICE")
@Data
@AllArgsConstructor
public class MultipleChoiceAnswer implements Answer {
    private int choice;

    @Override
    public boolean isEquals(Answer answer) {
        if (answer instanceof MultipleChoiceAnswer mcAnswer) {
            return choice == mcAnswer.getChoice();
        }

        return false;
    }
}
