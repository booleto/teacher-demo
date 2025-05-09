package com.tophat.teacherdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Student")
@Data
@Builder
public class Student {
    @Id
    private ObjectId id;
    private String username;
    private String displayName;
    private List<ObjectId> pendingAssignments;
}
