package com.tophat.teacherdemo.controller.vo;

import com.tophat.teacherdemo.controller.vo.answer.AnswerDTO;
import com.tophat.teacherdemo.entity.SubmissionItem;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SubmissionItemDTO {
    AnswerDTO answer;
    ObjectId problemId;

    public SubmissionItem toEntity() {
        return new SubmissionItem(answer.toAnswer(), problemId);
    }
}
