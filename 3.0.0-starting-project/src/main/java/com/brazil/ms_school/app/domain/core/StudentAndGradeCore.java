package com.brazil.ms_school.app.domain.core;

import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.domain.model.MathGrade;
import com.brazil.ms_school.app.port.out.MathGradesPort;
import com.brazil.ms_school.app.port.out.StudentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
/*
 transacional - porque permitira que o spring gerencie as
 transacoes em segundo plano.
 */
@Transactional
@RequiredArgsConstructor
public class StudentAndGradeCore {

    private final StudentRepositoryPort studentRepositoryPort;

    @Qualifier("mathGrades")
    private final MathGrade mathGrade;

    private final MathGradesPort mathGradesPort;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(1);
        studentRepositoryPort.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentRepositoryPort.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentRepositoryPort.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        Iterable<CollegeStudent> collegeStudents = studentRepositoryPort.findAll();
        return collegeStudents;
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        // verificando se o aluno existe
        if (!checkIfStudentIsNull(studentId)) {
            return false;
        }

        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesPort.save(mathGrade);

                return true;
            }
        }

        return false;
    }

}
