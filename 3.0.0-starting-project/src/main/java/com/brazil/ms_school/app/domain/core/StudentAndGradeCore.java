package com.brazil.ms_school.app.domain.core;

import com.brazil.ms_school.app.domain.model.*;
import com.brazil.ms_school.app.port.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    /*
      qualifier -> permite que se referencie pelo nome o objeto
      que esta injetado.
     */
    @Qualifier("mathGrades")
    private final MathGrade mathGrade;

    private final MathGradesPort mathGradesPort;

    @Qualifier("scienceGrades")
    private final ScienceGrade scienceGrade;

    private final ScienceGradesPort scienceGradesPort;

    @Qualifier("historyGrades")
    private final HistoryGrade historyGrade;

    private final HistoryGradesPort historyGradesPort;

    private final StudentGradesCore studentGradesCore;

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
            mathGradesPort.deleteByStudentId(id);
            scienceGradesPort.deleteByStudentId(id);
            historyGradesPort.deleteByStudentId(id);
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
            if (gradeType.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesPort.save(scienceGrade);

                return true;
            }
            if (gradeType.equals("history")) {
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesPort.save(historyGrade);

                return true;
            }
        }

        return false;
    }

    public int deleteGrade(int id, String gradeType) {
        int studentId = 0;

        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradesPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradesPort.deleteById(id);
        }
        if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradesPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradesPort.deleteById(id);
        }
        if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradesPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradesPort.deleteById(id);
        }

        return studentId;
    }

    public GradeBookCollegeStudent studentInformation(int id) {
        // recuperando o aluno do banco de dados
        Optional<CollegeStudent> student = studentRepositoryPort.findById(id);

        // referenciando as notas de matematica
        Iterable<MathGrade> mathGrades = mathGradesPort.findGradeByStudentId(id);
        // referenciando as notas de ciencia
        Iterable<ScienceGrade> scienceGrades = scienceGradesPort.findGradeByStudentId(id);
        // referenciando as notas de historia
        Iterable<HistoryGrade> historyGrades = historyGradesPort.findGradeByStudentId(id);

        // convertendo uma iterable em uma list
        List<GradePort> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<GradePort> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);

        List<GradePort> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        // enviando as notas
        studentGradesCore.setMathGradeResults(mathGradesList);
        studentGradesCore.setScienceGradeResults(scienceGradesList);
        studentGradesCore.setHistoryGradeResults(historyGradesList);

        GradeBookCollegeStudent gradeBookCollegeStudent = new GradeBookCollegeStudent(student.get().getId(),
                student.get().getFirstname(), student.get().getLastname(), student.get().getEmailAddress(),
                studentGradesCore);

        return gradeBookCollegeStudent;
    }

}
