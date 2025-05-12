package com.tophat.teacherdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@Builder
public class StudentPendingAssignment {
    private ObjectId assignmentId;
    private String title;
    private String description;
    private Instant deadline;
}
