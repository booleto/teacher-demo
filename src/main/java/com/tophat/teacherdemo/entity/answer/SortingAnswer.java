package com.tophat.teacherdemo.entity.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.IntStream;

import static com.tophat.teacherdemo.entity.answer.SortingAnswer.ALIAS;

@Document
@TypeAlias(ALIAS)
@Data
@AllArgsConstructor
public class SortingAnswer implements Answer {
    public static final String ALIAS = "SORTING";
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
