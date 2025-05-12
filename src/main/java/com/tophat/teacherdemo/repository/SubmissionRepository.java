package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {

    @Query(value = " { '_id' : ?0 , 'status' : ?1 } ", delete = true)
    Submission deleteByIdIfStatusEquals(ObjectId id, Submission.Status status);
}
