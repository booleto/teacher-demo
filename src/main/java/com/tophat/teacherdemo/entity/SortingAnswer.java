package com.tophat.teacherdemo.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.List;
import java.util.stream.IntStream;

@JsonTypeName("SORTING")
@Data
public class SortingAnswer implements Answer {
    private List<String> orderedAnswers;

    @Override
    public boolean isEquals(Answer answer) {
        if (answer instanceof SortingAnswer sortAnswer) {
            if (sortAnswer.orderedAnswers.size() != orderedAnswers.size()) return false;

            return IntStream.range(0, orderedAnswers.size())
                    .allMatch(i -> orderedAnswers.get(i)
                            .equals(sortAnswer.getOrderedAnswers().get(i)));
        }

        return false;
    }
}
