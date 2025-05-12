package com.tophat.teacherdemo.entity;

import com.tophat.teacherdemo.entity.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Document("Assignment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Assignment {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private Instant submissionDeadline;
    private Status status;
    private Map<String, Object> metadata;
    private List<StudentSubmissionStatus> submissionStatus;

    @DocumentReference
    private List<Problem<Answer>> problems;

    public enum Status {
        DRAFT, READY, IN_USE, FINISHED
    }
}
