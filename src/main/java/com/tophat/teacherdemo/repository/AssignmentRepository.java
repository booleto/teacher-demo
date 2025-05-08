package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, ObjectId> {

}
