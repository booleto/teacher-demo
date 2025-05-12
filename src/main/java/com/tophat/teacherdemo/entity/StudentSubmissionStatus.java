package com.tophat.teacherdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder
public class StudentSubmissionStatus {
    private ObjectId studentId;
    private String displayName;
    private ObjectId submissionId;
    private Status status;

    public enum Status {
        UNSUBMITTED, TURNED_IN, MISSED
    }
}
