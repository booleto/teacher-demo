package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.controller.vo.answer.AnswerDTO;
import com.tophat.teacherdemo.entity.SubmissionItem;
import lombok.Data;
import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotNull;

@Data
public class SubmissionItemDTO {
    @NotNull(message = "answer not found in submissionItem")
    AnswerDTO answer;

    @NotNull(message = "problem not found in submissionItem")
    ObjectId problemId;

    public SubmissionItem toEntity() {
        return new SubmissionItem(answer.toAnswer(), problemId);
    }
}
