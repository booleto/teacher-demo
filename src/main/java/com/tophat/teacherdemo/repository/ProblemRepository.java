package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.answer.Answer;
import com.tophat.teacherdemo.entity.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProblemRepository extends MongoRepository<Problem<Answer>, ObjectId> {

    @Query(" { '$text': { '$search': ?0 } } ")
    Page<Problem<Answer>> findAllBy(String textQuery, Pageable pageable);
}
