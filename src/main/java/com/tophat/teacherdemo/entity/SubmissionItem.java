package com.tophat.teacherdemo.entity;

import com.tophat.teacherdemo.entity.answer.Answer;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class SubmissionItem {
    Answer submittedAnswer;
    ObjectId problemId;
}
