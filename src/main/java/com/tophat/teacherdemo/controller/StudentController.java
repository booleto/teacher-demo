package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.StudentCreateRequest;
import com.tophat.teacherdemo.entity.Student;
import com.tophat.teacherdemo.exception.ResourceNotFoundException;
import com.tophat.teacherdemo.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/student")
@Validated
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody @Valid StudentCreateRequest student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable ObjectId id) {
        Optional<Student> foundStudent = studentService.findStudentById(id);
        return foundStudent.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Student %s not found", id.toString())
                ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudentProfile(@PathVariable ObjectId id, @RequestBody @Valid StudentCreateRequest student) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, student);
        return updatedStudent.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Student %s not found", id.toString())
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable ObjectId id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
