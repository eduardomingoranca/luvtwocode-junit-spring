package com.brazil.luvtwocode.adapter;

import com.brazil.luvtwocode.domain.core.StudentAndGradeCore;
import com.brazil.luvtwocode.domain.exception.StudentOrGradeNotFoundException;
import com.brazil.luvtwocode.domain.exception.response.StudentOrGradeErrorResponse;
import com.brazil.luvtwocode.domain.model.CollegeStudent;
import com.brazil.luvtwocode.domain.model.GradeBook;
import com.brazil.luvtwocode.domain.model.GradeBookCollegeStudent;
import com.brazil.luvtwocode.port.in.GradeBookPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequiredArgsConstructor
public class GradeBookAdapter implements GradeBookPort {


    private final StudentAndGradeCore studentCore;


    @Autowired
    private GradeBook gradeBook;


    @RequestMapping(value = "/", method = GET)
    public List<GradeBookCollegeStudent> getStudents() {
        gradeBook = studentCore.getGradeBook();
        return gradeBook.getStudents();
    }


    @GetMapping("/studentInformation/{id}")
    public GradeBookCollegeStudent studentInformation(@PathVariable int id) {

        if (!studentCore.checkIfStudentIsNull(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }
        GradeBookCollegeStudent studentEntity = studentCore.studentInformation(id);

        return studentEntity;
    }


    @PostMapping(value = "/")
    public ResponseEntity<List<GradeBookCollegeStudent>> createStudent(@RequestBody CollegeStudent student) {
        studentCore.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
        gradeBook = studentCore.getGradeBook();
        return new ResponseEntity<>(gradeBook.getStudents(), CREATED);
    }


    @DeleteMapping("/student/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable int id) {

        if (!studentCore.checkIfStudentIsNull(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        studentCore.deleteStudent(id);
        gradeBook = studentCore.getGradeBook();
        return new ResponseEntity<>(NO_CONTENT);
    }


    @PostMapping(value = "/grades")
    public ResponseEntity<GradeBookCollegeStudent> createGrade(@RequestParam("grade") double grade,
                                                               @RequestParam("gradeType") String gradeType,
                                                               @RequestParam("studentId") int studentId) {

        if (!studentCore.checkIfStudentIsNull(studentId)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        boolean success = studentCore.createGrade(grade, studentId, gradeType);

        if (!success) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        GradeBookCollegeStudent studentEntity = studentCore.studentInformation(studentId);

        if (studentEntity == null) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        return new ResponseEntity<>(studentEntity, CREATED);
    }

    @DeleteMapping("/grades/{id}/{gradeType}")
    public ResponseEntity<GradeBookCollegeStudent> deleteGrade(@PathVariable int id, @PathVariable String gradeType) {

        int studentId = studentCore.deleteGrade(id, gradeType);

        if (studentId == 0) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        studentCore.studentInformation(studentId);

        return new ResponseEntity<>(NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(StudentOrGradeNotFoundException exc) {

        StudentOrGradeErrorResponse error = new StudentOrGradeErrorResponse();

        error.setStatus(NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(Exception exc) {

        StudentOrGradeErrorResponse error = new StudentOrGradeErrorResponse();

        error.setStatus(BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}
