package com.brazil.ms_school.app.adapter;

import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.domain.model.GradeBook;
import com.brazil.ms_school.app.domain.core.StudentAndGradeCore;
import com.brazil.ms_school.app.port.in.GradeBookPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class GradeBookAdapter implements GradeBookPort {

    @Autowired
    private GradeBook gradeBook;

    @Autowired
    private StudentAndGradeCore studentCore;

    @Override
    @RequestMapping(value = "/", method = GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradebook();
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
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradebook();
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
        Iterable<CollegeStudent> collegeStudents = studentCore.getGradebook();
        m.addAttribute("students", collegeStudents);

        return "index";
    }

    @Override
    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

}
