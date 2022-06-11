package com.brazil.ms_school.app.adapter;

import com.brazil.ms_school.app.domain.core.StudentAndGradeCore;
import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.domain.model.GradeBook;
import com.brazil.ms_school.app.domain.model.GradeBookCollegeStudent;
import com.brazil.ms_school.app.port.in.GradeBookPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequiredArgsConstructor
public class GradeBookAdapter implements GradeBookPort {

    private final GradeBook gradeBook;
    private final StudentAndGradeCore studentCore;

    @Override
    @RequestMapping(value = "/", method = GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradeBook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    /*
        quando os parametros vierem entao o spring faz o mepeamento
        dos parametros para um determinado estudante universitario.
     */
    @Override
    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentCore.createStudent(student.getFirstname(), student.getLastname(),
                student.getEmailAddress());
        /*
          depois de criar este aluno, obteremos uma lista dos alunos e
          adiciona-los como um atributo de modelo.
         */
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradeBook();
        m.addAttribute("students", collegeStudents);

        return "index";
    }

    @Override
    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {

        if (!studentCore.checkIfStudentIsNull(id)) {
            return "error";
        }

        studentCore.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradeBook();
        m.addAttribute("students", collegeStudents);

        return "index";
    }

    @Override
    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {

        if (!studentCore.checkIfStudentIsNull(id)) {
            return "error";
        }

        GradeBookCollegeStudent collegeStudent = studentCore.studentInformation(id);

        m.addAttribute("student", collegeStudent);
        if (collegeStudent.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getMathGradeResults()
            ));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }

        if (collegeStudent.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }

        if (collegeStudent.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            m.addAttribute("historyAverage", "N/A");
        }

        return "studentInformation";
    }

    @PostMapping(value = "/grades")
    public String createGrade(@RequestParam("grade") double grade,
                              @RequestParam("gradeType") String gradeType,
                              @RequestParam("studentId") int studentId,
                              Model m) {

        if (!studentCore.checkIfStudentIsNull(studentId)) {
            return "error";
        }

        boolean success = studentCore.createGrade(grade, studentId, gradeType);

        if (!success) {
            return "error";
        }

        GradeBookCollegeStudent collegeStudent = studentCore.studentInformation(studentId);

        m.addAttribute("student", collegeStudent);
        if (collegeStudent.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getMathGradeResults()
            ));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }

        if (collegeStudent.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }

        if (collegeStudent.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", collegeStudent.getStudentGrades().findGradePointAverage(
                    collegeStudent.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            m.addAttribute("historyAverage", "N/A");
        }

        return "studentInformation";
    }
    

}
