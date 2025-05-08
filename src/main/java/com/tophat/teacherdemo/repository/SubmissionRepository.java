package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {

}
