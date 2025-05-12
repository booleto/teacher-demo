package com.tophat.teacherdemo.service.impl;

import com.tophat.teacherdemo.controller.vo.StudentCreateRequest;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Student;
import com.tophat.teacherdemo.entity.StudentPendingAssignment;
import com.tophat.teacherdemo.repository.StudentRepository;
import com.tophat.teacherdemo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public Optional<Student> findStudentById(ObjectId id) {
        return studentRepository.findById(id);
    }


    @Override
    public Student createStudent(StudentCreateRequest student) {
        return studentRepository.save(
                Student.builder()
                        .username(student.getUsername())
                        .displayName(student.getDisplayName())
                .build());
    }


    @Override
    public Optional<Student> updateStudent(ObjectId id, StudentCreateRequest studentRequest) {
        Optional<Student> studentSearch = findStudentById(id);
        if (studentSearch.isEmpty()) return Optional.empty();
        Student student = studentSearch.get();

        Optional.ofNullable(studentRequest.getUsername()).ifPresent(student::setUsername);
        Optional.ofNullable(studentRequest.getDisplayName()).ifPresent(student::setDisplayName);

        return Optional.of(studentRepository.save(student));
    }


    @Override
    public void deleteStudent(ObjectId id) {
        studentRepository.deleteById(id);
    }


    @Override
    public void addPendingAssignment(List<ObjectId> studentIds, Assignment assignment) {
        throwIfStudentNotExist(studentIds);

        studentRepository.addPendingAssignmentById(studentIds, StudentPendingAssignment.builder()
                .assignmentId(assignment.getId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .deadline(assignment.getSubmissionDeadline())
                .build()
        );
    }


    @Override
    public void removePendingAssignment(List<ObjectId> studentIds, ObjectId assignmentId) {
        throwIfStudentNotExist(studentIds);
        studentRepository.removePendingAssignmentById(studentIds, assignmentId);
    }


    private void throwIfStudentNotExist(List<ObjectId> studentIds) {
        studentIds.parallelStream().forEach(studentId -> {
            Optional<Student> studentSearch = findStudentById(studentId);
            if (studentSearch.isEmpty())
                throw new IllegalArgumentException(String.format("Student with id %s not found", studentId));
        });
    }
}
