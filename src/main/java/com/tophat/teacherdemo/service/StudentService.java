package com.tophat.teacherdemo.service;

import com.tophat.teacherdemo.controller.vo.StudentCreateRequest;
import com.tophat.teacherdemo.entity.Assignment;
import com.tophat.teacherdemo.entity.Student;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing student records and their pending assignments.
 */
public interface StudentService {
    /**
     * Finds a student by their unique identifier.
     *
     * @param id the {@link ObjectId} of the student to find.
     * @return an {@link Optional} containing the {@link Student} if found, or empty if not found.
     */
    Optional<Student> findStudentById(ObjectId id);

    /**
     * Creates a new student record.
     *
     * @param student the {@link StudentCreateRequest} containing the student details.
     * @return the created {@link Student} object.
     */
    Student createStudent(StudentCreateRequest student);

    /**
     * Updates an existing student's information.
     *
     * @param id      the {@link ObjectId} of the student to update.
     * @param student the {@link StudentCreateRequest} containing the updated student details.
     * @return an {@link Optional} containing the updated {@link Student} if the student exists, or empty if not found.
     */
    Optional<Student> updateStudent(ObjectId id, StudentCreateRequest student);

    /**
     * Deletes a student by their unique identifier.
     *
     * @param id the {@link ObjectId} of the student to delete.
     */
    void deleteStudent(ObjectId id);

    /**
     * Adds a pending assignment to a list of students.
     *
     * @param studentIds a list of {@link ObjectId} representing the students.
     * @param assignment the {@link Assignment} to add as pending.
     */
    void addPendingAssignment(List<ObjectId> studentIds, Assignment assignment);

    /**
     * Removes a pending assignment from a list of students.
     *
     * @param studentIds  a list of {@link ObjectId} representing the students.
     * @param assignmentId the {@link ObjectId} of the assignment to remove.
     */
    void removePendingAssignment(List<ObjectId> studentIds, ObjectId assignmentId);
}
