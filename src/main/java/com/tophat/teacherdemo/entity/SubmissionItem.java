package com.tophat.teacherdemo.entity;

import com.tophat.teacherdemo.controller.vo.answer.AnswerDTO;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SubmissionItem {
    AnswerDTO answer;
    ObjectId problemId;
}
