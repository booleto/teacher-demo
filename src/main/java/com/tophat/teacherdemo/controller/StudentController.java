package com.tophat.teacherdemo.controller;

import com.tophat.teacherdemo.controller.vo.StudentCreateRequest;
import com.tophat.teacherdemo.entity.Student;
import com.tophat.teacherdemo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacherdemo/v0/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody StudentCreateRequest student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable ObjectId id) {
        Optional<Student> foundStudent = studentService.findStudentById(id);
        return foundStudent.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> updateStudentProfile(@PathVariable ObjectId id, StudentCreateRequest student) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, student);
        return updatedStudent.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable ObjectId id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
