package com.tophat.teacherdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document("Submission")
@Builder
@Data
public class Submission {
    @Id
    private ObjectId submissionId;
    private ObjectId studentId;
    private ObjectId assignmentId;
    private List<SubmissionItem> submissionItems;
    private Map<String, Object> metadata;
    private float grade;
    private Status status;

    public enum Status {
        DRAFT, TURNED_IN, GRADED
    }
}
