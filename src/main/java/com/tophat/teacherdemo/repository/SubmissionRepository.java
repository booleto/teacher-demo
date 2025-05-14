package com.tophat.teacherdemo.repository;

import com.tophat.teacherdemo.controller.vo.ProblemAccuracy;
import com.tophat.teacherdemo.entity.Submission;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {

    @Query(value = " { '_id' : ?0 , 'status' : ?1 } ", delete = true)
    Submission deleteByIdIfStatusEquals(ObjectId id, Submission.Status status);

    @Aggregation(pipeline = { "{'$match' : { 'assignmentId': ?0, 'status': 'TURNED_IN' }}",
            "{ '$group': { '_id': '$assignmentId', 'averageGrade': { '$avg': '$grade' } } }" })
    Float averageGradeByAssignmentId(ObjectId assignmentId);

    @Aggregation(pipeline = { "{'$match' : { 'assignmentId': ?0, 'status': 'TURNED_IN' }}",
            "{ '$group': { '_id': '$assignmentId', 'averageGrade': { '$max': '$grade' } } }" })
    Float topGradeByAssignmentId(ObjectId assignmentId);

    @Aggregation(pipeline = { "{'$match' : { 'assignmentId': ?0, 'status': 'TURNED_IN' }}",
            "{ '$group': { '_id': '$assignmentId', 'averageGrade': { '$min': '$grade' } } }" })
    Float botGradeByAssignmentId(ObjectId assignmentId);

    @Aggregation(pipeline = {
        "{ '$match': { 'assignmentId': ?0 } }",
        "{ $project: { grading_entries: { $objectToArray: '$metadata.grading_results' } } }",
        "{ '$unwind': '$grading_entries' }",
        "{ '$group': { '_id': '$grading_entries.k', 'accuracy': { '$avg': { '$cond': [ { '$eq': [ '$grading_entries.v', true ] }, 1, 0 ] } } } }"
    })
    List<ProblemAccuracy> averageGradeByProblem(ObjectId assignmentId);
}
