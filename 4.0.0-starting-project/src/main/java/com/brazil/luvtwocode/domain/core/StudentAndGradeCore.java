package com.brazil.luvtwocode.domain.core;

import com.brazil.luvtwocode.domain.model.*;
import com.brazil.luvtwocode.port.in.GradePort;
import com.brazil.luvtwocode.port.out.HistoryGradesDaoPort;
import com.brazil.luvtwocode.port.out.MathGradesDaoPort;
import com.brazil.luvtwocode.port.out.ScienceGradesDaoPort;
import com.brazil.luvtwocode.port.out.StudentDaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentAndGradeCore {

    private final StudentDaoPort studentDaoPort;

    private final MathGradesDaoPort mathGradesDaoPort;

    private final ScienceGradesDaoPort scienceGradesDaoPort;

    private final HistoryGradesDaoPort historyGradesDaoPort;

    @Qualifier("mathGrades")
    private final MathGrade mathGrade;

    @Qualifier("scienceGrades")
    private final ScienceGrade scienceGrade;

    @Qualifier("historyGrades")
    private final HistoryGrade historyGrade;

    private final StudentGrades studentGrades;

    public void createStudent(String firstname, String lastname, String emailAddress){

        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);

        student.setId(0);

        studentDaoPort.save(student);
    }

    public void deleteStudent(int id){
        if (checkIfStudentIsNull(id)) {
            studentDaoPort.deleteById(id);
            mathGradesDaoPort.deleteByStudentId(id);
            scienceGradesDaoPort.deleteByStudentId(id);
            historyGradesDaoPort.deleteByStudentId(id);
        }
    }

    public boolean checkIfStudentIsNull(int id){
        Optional<CollegeStudent> student = studentDaoPort.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }

    public GradeBookCollegeStudent studentInformation(int id) {
        Optional<CollegeStudent> student = studentDaoPort.findById(id);

        if (!student.isPresent()) {
            return null;
        }

        Iterable<MathGrade> mathGrades = mathGradesDaoPort.findGradeByStudentId(id);

        Iterable<ScienceGrade> scienceGrades = scienceGradesDaoPort.findGradeByStudentId(id);

        Iterable<HistoryGrade> historyGrades = historyGradesDaoPort.findGradeByStudentId(id);

        List<GradePort> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<GradePort> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);

        List<GradePort> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);
        studentGrades.setHistoryGradeResults(historyGradesList);

        GradeBookCollegeStudent gradebookCollegeStudent = new GradeBookCollegeStudent(student.get().getId(),
                student.get().getFirstname(), student.get().getLastname(),
                student.get().getEmailAddress(), studentGrades);

        return gradebookCollegeStudent;
    }

    public boolean checkIfGradeIsNull(int id, String gradeType){
        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradesDaoPort.findById(id);
            if (grade.isPresent()) {
                return true;
            }
        }
        if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradesDaoPort.findById(id);
            if (grade.isPresent()) {
                return true;
            }
        }
        if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradesDaoPort.findById(id);
            if (grade.isPresent()) {
                return true;
            }
        }

        return false;
    }

    public int deleteGrade(int id, String gradeType) {

        int studentId = 0;

        if (gradeType.equals("math")) {
            Optional<MathGrade> grade = mathGradesDaoPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradesDaoPort.deleteById(id);
        }

        if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradesDaoPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradesDaoPort.deleteById(id);
        }

        if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradesDaoPort.findById(id);
            if (!grade.isPresent()) {
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradesDaoPort.deleteById(id);
        }

        return studentId;
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDaoPort.save(mathGrade);
                return true;
            }

            if (gradeType.equals("science")) {
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDaoPort.save(scienceGrade);
                return true;
            }

            if (gradeType.equals("history")) {
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDaoPort.save(historyGrade);
                return true;
            }
        }
        return false;
    }

    public GradeBook getGradeBook() {

        Iterable<CollegeStudent> collegeStudents = studentDaoPort.findAll();

        Iterable<MathGrade> mathGrades = mathGradesDaoPort.findAll();

        Iterable<ScienceGrade> scienceGrades = scienceGradesDaoPort.findAll();

        Iterable<HistoryGrade> historyGrades = historyGradesDaoPort.findAll();

        GradeBook gradebook = new GradeBook();

        for (CollegeStudent collegeStudent : collegeStudents) {
            List<GradePort> mathGradesPerStudent = new ArrayList<>();
            List<GradePort> scienceGradesPerStudent = new ArrayList<>();
            List<GradePort> historyGradesPerStudent = new ArrayList<>();

            for (MathGrade grade : mathGrades) {
                if (grade.getStudentId() == collegeStudent.getId()) {
                    mathGradesPerStudent.add(grade);
                }
            }
            for (ScienceGrade grade : scienceGrades) {
                if (grade.getStudentId() == collegeStudent.getId()) {
                    scienceGradesPerStudent.add(grade);
                }
            }

            for (HistoryGrade grade : historyGrades) {
                if (grade.getStudentId() == collegeStudent.getId()) {
                    historyGradesPerStudent.add(grade);
                }
            }

            studentGrades.setMathGradeResults(mathGradesPerStudent);
            studentGrades.setScienceGradeResults(scienceGradesPerStudent);
            studentGrades.setHistoryGradeResults(historyGradesPerStudent);

            GradeBookCollegeStudent gradebookCollegeStudent = new GradeBookCollegeStudent(collegeStudent.getId(),
                    collegeStudent.getFirstname(), collegeStudent.getLastname(),
                    collegeStudent.getEmailAddress(), studentGrades);

            gradebook.getStudents().add(gradebookCollegeStudent);
        }

        return gradebook;
    }

    public void configureStudentInformationModel(int id, Model m) {

        GradeBookCollegeStudent studentEntity = studentInformation(id);

        m.addAttribute("student", studentEntity);

        if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", studentEntity.getStudentGrades()
                    .findGradePointAverage(studentEntity.getStudentGrades().getMathGradeResults()));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades()
                    .findGradePointAverage(studentEntity.getStudentGrades().getScienceGradeResults()));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }

        if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", studentEntity.getStudentGrades()
                    .findGradePointAverage(studentEntity.getStudentGrades().getHistoryGradeResults()));
        } else {
            m.addAttribute("historyAverage", "N/A");
        }
    }

}
