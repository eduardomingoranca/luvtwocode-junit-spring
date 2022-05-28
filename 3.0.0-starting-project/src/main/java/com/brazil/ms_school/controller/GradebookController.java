package com.brazil.ms_school.controller;

import com.brazil.ms_school.models.CollegeStudent;
import com.brazil.ms_school.models.Gradebook;
import com.brazil.ms_school.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class GradebookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentService;

    @RequestMapping(value = "/", method = GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    /*
        quando os parametros vierem entao o spring faz o mepeamento
        dos parametros para um determinado estudante universitario.
     */
    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
        studentService.createStudent(student.getFirstname(), student.getLastname(),
                student.getEmailAddress());
        /*
          depois de criar este aluno, obteremos uma lista dos alunos e
          adiciona-los como um atributo de modelo.
         */
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);

        return "index";
    }

    @GetMapping("/delete/student/{id}")
    public String deleteStudent(@PathVariable int id, Model m) {
        studentService.deleteStudent(id);
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        m.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }
}
