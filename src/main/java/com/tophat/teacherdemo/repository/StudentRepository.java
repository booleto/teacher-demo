package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.entity.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, ObjectId> {

}
