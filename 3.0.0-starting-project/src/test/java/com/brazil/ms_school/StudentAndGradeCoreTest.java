package com.brazil.ms_school;

import com.brazil.ms_school.app.domain.core.StudentAndGradeCore;
import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.domain.model.HistoryGrade;
import com.brazil.ms_school.app.domain.model.MathGrade;
import com.brazil.ms_school.app.domain.model.ScienceGrade;
import com.brazil.ms_school.app.port.out.HistoryGradesPort;
import com.brazil.ms_school.app.port.out.MathGradesPort;
import com.brazil.ms_school.app.port.out.ScienceGradesPort;
import com.brazil.ms_school.app.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 permite carregar propriedades durante o teste, neste caso
 sera referenciado a application.properties.
 */
@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeCoreTest {

    /*
      JdbcTemplate e uma classe auxiliar fornecido pela
      estrutura do spring framework que permite executar
      operacoes JDBC, ou seja, query sql database.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentAndGradeCore studentCore;

    @Autowired
    private StudentRepositoryPort studentRepositoryPort;

    @Autowired
    private MathGradesPort mathGradesPort;

    @Autowired
    private ScienceGradesPort scienceGradesPort;

    @Autowired
    private HistoryGradesPort historyGradesPort;

    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute("insert into student(id, email_address, firstname, lastname)" +
                "values (1, 'lon.hammond@luv2code_school.com', 'Lon', 'Hammond')");

        jdbcTemplate.execute("insert into math_grade(id, grade, student_id) values (1, 100.0, 1)");

        jdbcTemplate.execute("insert into science_grade(id, grade, student_id) values (1, 100.0, 1)");

        jdbcTemplate.execute("insert into history_grade(id, grade, student_id) values (1, 100.0, 1)");
    }

    @Test
    void createStudentService() {
        studentCore.createStudent("Noah", "Calhoun",
                "noah.calhoun@luv2code_school.com");

        CollegeStudent student = studentRepositoryPort
                .findByEmailAddress("noah.calhoun@luv2code_school.com");

        assertEquals("noah.calhoun@luv2code_school.com",
                student.getEmailAddress(), "find by email");
    }

    @Test
    void isStudentNullCheck() {
        assertTrue(studentCore.checkIfStudentIsNull(1));
        assertFalse(studentCore.checkIfStudentIsNull(0));
    }

    @Test
    void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentRepositoryPort.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        
        studentCore.deleteStudent(1);

        // recuperando o aluno excluido
        deletedCollegeStudent = studentRepositoryPort.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
    }

    /*
       carregando e executando o SQL para o teste, o arquivo sql sera executado e
       carregado primeiro antes do teste.
     */
    @Sql("/insertData.sql")
    @Test
    void getGradeBookService() {
        // obtendo uma lista ou colecao de todos os alunos do banco de dados.
        Iterable<CollegeStudent> iterableCollegeStudents = studentCore.getGradeBook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();

        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }

        assertEquals(5, collegeStudents.size());
    }

    @Test
    void createGradeService() {
        // create the grade
        assertTrue(studentCore.createGrade(80.50, 1, "math"));
        assertTrue(studentCore.createGrade(80.50, 1, "science"));
        assertTrue(studentCore.createGrade(80.50, 1, "history"));

        // get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesPort.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesPort.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesPort.findGradeByStudentId(1);

        // verify there is grade
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2, "Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2, "Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2, "Student has history grades");
    }

    @Test
    void createGradeServiceReturnFalse() {
        assertFalse(studentCore.createGrade(105, 1, "math"));
        assertFalse(studentCore.createGrade(-5, 1, "math"));
        assertFalse(studentCore.createGrade(80.5, 2, "math"));
        assertFalse(studentCore.createGrade(80.5, 1, "literature"));
    }

    @Test
    void deleteGradeService() {
        assertEquals(1, studentCore.deleteGrade(1, "math"),
                "Returns student id after delete");
        assertEquals(1, studentCore.deleteGrade(1, "science"),
                "Returns student id after delete");
        assertEquals(1, studentCore.deleteGrade(1, "history"),
                "Returns student id after delete");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("DELETE FROM math_grade");
        jdbcTemplate.execute("DELETE FROM science_grade");
        jdbcTemplate.execute("DELETE FROM history_grade");
    }

}
