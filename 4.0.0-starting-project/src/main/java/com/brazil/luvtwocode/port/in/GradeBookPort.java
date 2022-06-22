package com.brazil.luvtwocode.port.in;

import com.brazil.luvtwocode.domain.exception.StudentOrGradeNotFoundException;
import com.brazil.luvtwocode.domain.exception.response.StudentOrGradeErrorResponse;
import com.brazil.luvtwocode.domain.model.CollegeStudent;
import com.brazil.luvtwocode.domain.model.GradeBookCollegeStudent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GradeBookPort {
    List<GradeBookCollegeStudent> getStudents();

    GradeBookCollegeStudent studentInformation(int id);

    List<GradeBookCollegeStudent> createStudent(CollegeStudent student);

    List<GradeBookCollegeStudent> deleteStudent(int id);

    GradeBookCollegeStudent createGrade(double grade, String gradeType, int studentId);

    GradeBookCollegeStudent deleteGrade(int id, String gradeType);

    ResponseEntity<StudentOrGradeErrorResponse> handleException(StudentOrGradeNotFoundException exc);

    ResponseEntity<StudentOrGradeErrorResponse> handleException(Exception exc);
}
