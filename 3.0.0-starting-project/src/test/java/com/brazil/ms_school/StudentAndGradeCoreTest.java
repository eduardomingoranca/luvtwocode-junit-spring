package com.brazil.ms_school;

import com.brazil.ms_school.app.domain.core.StudentAndGradeCore;
import com.brazil.ms_school.app.domain.model.*;
import com.brazil.ms_school.app.port.out.HistoryGradesPort;
import com.brazil.ms_school.app.port.out.MathGradesPort;
import com.brazil.ms_school.app.port.out.ScienceGradesPort;
import com.brazil.ms_school.app.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeEach
    void setupDatabase() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @Test
    void createStudentService() {
        studentCore.createStudent("Isaac", "Cohen",
                "isaac.cohen@luv2code_school.com");

        CollegeStudent student = studentRepositoryPort
                .findByEmailAddress("isaac.cohen@luv2code_school.com");

        assertEquals("isaac.cohen@luv2code_school.com",
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
        Optional<MathGrade> deletedMathGrade = mathGradesPort.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradesPort.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradesPort.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        assertTrue(deletedMathGrade.isPresent());
        assertTrue(deletedHistoryGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());

        studentCore.deleteStudent(1);

        // recuperando o aluno excluido
        deletedCollegeStudent = studentRepositoryPort.findById(1);
        deletedMathGrade = mathGradesPort.findById(1);
        deletedHistoryGrade = historyGradesPort.findById(1);
        deletedScienceGrade = scienceGradesPort.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());
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

    @Test
    void deleteGradeServiceReturnStudentIdOfZero() {
        assertEquals(0, studentCore.deleteGrade(0, "science"),
                "No student should have 0 id");
        assertEquals(0, studentCore.deleteGrade(1, "literature"),
                "No student should have a literature class");
    }

    @Test
    void studentInformationService() {
        // obtendo o boletim escolar
        GradeBookCollegeStudent collegeStudent = studentCore.studentInformation(1);

        assertNotNull(collegeStudent);
        assertEquals(1, collegeStudent.getId());
        assertEquals("Joseph", collegeStudent.getFirstname());
        assertEquals("Mizrahi", collegeStudent.getLastname());
        assertEquals("joseph.mizrahi@luv2code_school.com", collegeStudent.getEmailAddress());
        assertTrue(collegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(collegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(collegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    void studentInformationServiceReturnNull() {
        GradeBookCollegeStudent collegeStudent = studentCore.studentInformation(0);
        assertNull(collegeStudent);
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

}
