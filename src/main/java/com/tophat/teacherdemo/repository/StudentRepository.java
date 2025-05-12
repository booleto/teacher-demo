package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Student;
import com.tophat.teacherdemo.entity.StudentPendingAssignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, ObjectId> {

    @Query("{ '_id' : { '$in' : ?0 } }")
    @Update("{ '$push' : { 'pendingAssignments' : ?1 } }")
    void addPendingAssignmentById(List<ObjectId> studentIds, StudentPendingAssignment assignment);

    @Query("{ '_id' : { '$in' : ?0 } }")
    @Update("{ '$pull' : { 'pendingAssignments' : { 'assignmentId' : ?1 } } }")
    void removePendingAssignmentById(List<ObjectId> studentIds, ObjectId assignmentId);
}
