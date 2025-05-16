package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.controller.vo.answer.AnswerDTO;
import com.tophat.teacherdemo.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProblemCreateRequest {
    @NotNull(message = "title must not be null")
    @Size(max = 200, message = "title is too long")
    private String title;

    @NotNull(message = "description must not be null")
    @Size(max = 1000, message = "description is too long")
    private String description;

    @NotNull(message = "questionText must not be null")
    @Size(max = 2500, message = "questionText is too long")
    private String questionText;

    @NotNull(message = "type must not be null")
    private Problem.Type type;

    @NotNull(message = "correctAnswer must not be null")
    private AnswerDTO correctAnswer;

    private List<String> choices;
    private Map<String, Object> metadata;
}
