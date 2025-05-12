package com.tophat.teacherdemo.entity;

import com.tophat.teacherdemo.entity.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;


@AllArgsConstructor
@Data
public class SubmissionItem {
    Answer answer;
    ObjectId problemId;
}
