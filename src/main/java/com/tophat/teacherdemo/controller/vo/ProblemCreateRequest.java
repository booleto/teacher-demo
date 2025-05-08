package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.entity.Answer;
import com.tophat.teacherdemo.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProblemCreateRequest {
    private String title;
    private String description;
    private String questionText;
    private Problem.Type type;
    private Answer correctAnswer;
    private List<String> choices;
    private Map<String, Object> metadata;
}
