package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AssignmentRepository extends MongoRepository<Assignment, ObjectId> {

    @Query(" { '$text': { '$search': ?0 } } ")
    Page<Assignment> findAllBy(String textQuery, Pageable pageable);
}
