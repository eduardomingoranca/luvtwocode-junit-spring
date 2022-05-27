package com.brazil.ms_school;

import com.brazil.ms_school.models.CollegeStudent;
import com.brazil.ms_school.repository.StudentDao;
import com.brazil.ms_school.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 permite carregar propriedades durante o teste, neste caso
 sera referenciado a application.properties.
 */
@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {

    /*
      JdbcTemplate e uma classe auxiliar fornecido pela
      estrutura do spring framework que permite executar
      operacoes JDBC, ou seja, query sql database.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute("insert into student(id, email_address, firstname, lastname)" +
                "values (1, 'eric.roby@luv2code_school.com', 'Eric', 'Roby')");
    }

    @Test
    void createStudentService() {
        studentService.createStudent("Chad", "Darby",
                "chad.darby@luv2code_school.com");

        CollegeStudent student = studentDao
                .findByEmailAddress("chad.darby@luv2code_school.com");

        assertEquals("chad.darby@luv2code_school.com", 
                student.getEmailAddress(), "find by email");
    }

    @Test
    void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        
        studentService.deleteStudent(1);

        // recuperando o aluno excluido
        deletedCollegeStudent = studentDao.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
    }

}
