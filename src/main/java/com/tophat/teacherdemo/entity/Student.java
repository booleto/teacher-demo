package com.tophat.teacherdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Document("Student")
@Data
@Builder
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = 1707L;

    @Id
    private ObjectId id;
    private String username;
    private String displayName;
    private List<StudentPendingAssignment> pendingAssignments;
}
