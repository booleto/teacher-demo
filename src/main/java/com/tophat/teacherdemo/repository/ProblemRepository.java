package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Answer;
import com.tophat.teacherdemo.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem<Answer>, ObjectId> {

}
