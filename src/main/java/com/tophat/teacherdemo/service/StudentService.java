package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.StudentCreateRequest;
import com.tophat.teacherdemo.entity.Student;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface StudentService {
    Optional<Student> findStudentById(ObjectId id);
    Student createStudent(StudentCreateRequest student);
    Optional<Student> updateStudent(ObjectId id, StudentCreateRequest student);
    void deleteStudent(ObjectId id);
}
