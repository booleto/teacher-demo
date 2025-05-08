package com.tophat.teacherdemo.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@JsonTypeName("MULTIPLE_CHOICE")
@Data
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
