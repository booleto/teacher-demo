package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.entity.Problem;
import com.tophat.teacherdemo.entity.answer.Answer;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class AssignmentPublicView {
    private ObjectId id;
    private String title;
    private String description;
    private Instant submissionDeadline;
    private List<Problem<Answer>> problems;
}
