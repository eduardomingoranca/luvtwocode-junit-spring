package com.brazil.luvtwocode.domain.core;

import com.brazil.luvtwocode.domain.core.StudentAndGradeCore;
import com.brazil.luvtwocode.domain.model.*;
import com.brazil.luvtwocode.port.out.HistoryGradesDaoPort;
import com.brazil.luvtwocode.port.out.MathGradesDaoPort;
import com.brazil.luvtwocode.port.out.ScienceGradesDaoPort;
import com.brazil.luvtwocode.port.out.StudentDaoPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class StudentAndGradeCoreTest {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentDaoPort studentDaoPort;

    @Autowired
    private MathGradesDaoPort mathGradesDaoPort;

    @Autowired
    private ScienceGradesDaoPort scienceGradesDaoPort;

    @Autowired
    private HistoryGradesDaoPort historyGradesDaoPort;

    @Autowired
    private StudentAndGradeCore studentCore;

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
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @Test
    void isStudentNullCheck() {
        assertTrue(studentCore.checkIfStudentIsNull(1), "@BeforeTransaction creates student : return true");
        assertFalse(studentCore.checkIfStudentIsNull(0), "No student should have 0 id : return false");
    }


    @Test
    void createStudentService() {
        studentCore.createStudent("Samuel", "Hammond", "samuel.hammond@luv2code_school.com");
        CollegeStudent student = studentDaoPort.findByEmailAddress("samuel.hammond@luv2code_school.com");
        assertEquals("samuel.hammond@luv2code_school.com", student.getEmailAddress(), "find by email");
    }

    @Test
    void deleteStudentService() {

        Optional<CollegeStudent> deletedCollegeStudent = studentDaoPort.findById(1);
        Optional<MathGrade> deletedMathGrade = mathGradesDaoPort.findById(1);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradesDaoPort.findById(1);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradesDaoPort.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "return true");
        assertTrue(deletedMathGrade.isPresent(), "return true");
        assertTrue(deletedScienceGrade.isPresent(),  "return true");
        assertTrue(deletedHistoryGrade.isPresent(), "return true");

        studentCore.deleteStudent(1);

        deletedCollegeStudent = studentDaoPort.findById(1);
        deletedMathGrade = mathGradesDaoPort.findById(1);
        deletedScienceGrade = scienceGradesDaoPort.findById(1);
        deletedHistoryGrade = historyGradesDaoPort.findById(1);

        assertFalse(deletedCollegeStudent.isPresent(), "return false");
        assertFalse(deletedMathGrade.isPresent(), "return false");
        assertFalse(deletedScienceGrade.isPresent(),  "return false");
        assertFalse(deletedHistoryGrade.isPresent(), "return false");
    }


    @Test
    void studentInformationService() {
        GradeBookCollegeStudent gradebookCollegeStudentTest = studentCore.studentInformation(1);

        assertNotNull(gradebookCollegeStudentTest);
        assertEquals(1, gradebookCollegeStudentTest.getId());
        assertNotNull(gradebookCollegeStudentTest.getFirstname());
        assertNotNull(gradebookCollegeStudentTest.getLastname());
        assertNotNull(gradebookCollegeStudentTest.getEmailAddress());
        assertNotNull(gradebookCollegeStudentTest.getStudentGrades().getMathGradeResults());
        assertNotNull(gradebookCollegeStudentTest.getStudentGrades().getScienceGradeResults());
        assertNotNull(gradebookCollegeStudentTest.getStudentGrades().getHistoryGradeResults());

        assertEquals("Lon", gradebookCollegeStudentTest.getFirstname());
        assertEquals("Hammond", gradebookCollegeStudentTest.getLastname());
        assertEquals("lon.hammond@luv2code_school.com", gradebookCollegeStudentTest.getEmailAddress());
    }

    @Test
    void isGradeNullCheck() {

        assertTrue(studentCore.checkIfGradeIsNull(1, "math"),
                "@BeforeTransaction creates student : return true");

        assertTrue(studentCore.checkIfGradeIsNull(1, "science"),
                "@BeforeTransaction creates student : return true");

        assertTrue(studentCore.checkIfGradeIsNull(1, "history"),
                "@BeforeTransaction creates student : return true");

        assertFalse(studentCore.checkIfGradeIsNull(0, "science"),
                "No student should have 0 id : return false");

        assertFalse(studentCore.checkIfGradeIsNull(0, "literature"),
                "No student should have 0 id : return false");
    }

    @Test
    void deleteGradeService() {

        assertEquals(1, studentCore.deleteGrade(1, "math"),
                "@BeforeTransaction creates student : return true");

        assertEquals(1, studentCore.deleteGrade(1, "science"),
                "@BeforeTransaction creates student : return true");

        assertEquals(1, studentCore.deleteGrade(1, "history"),
                "@BeforeTransaction creates student : return true");

        assertEquals(0, studentCore.deleteGrade(0, "science"),
                "No student should have 0 id : return false");

        assertEquals(0, studentCore.deleteGrade(1, "literature"),
                "No student should have 0 id : return false");
    }

    @Test
    void createGradeService() {

        studentCore.createGrade(80.50, 2, "math");
        studentCore.createGrade(80.50, 2, "science");
        studentCore.createGrade(80.50, 2, "history");
        studentCore.createGrade(80.50, 2, "literature");

        Iterable<MathGrade> mathGrades  = mathGradesDaoPort.findGradeByStudentId(2);

        Iterable<ScienceGrade> scienceGrades  = scienceGradesDaoPort.findGradeByStudentId(2);

        Iterable<HistoryGrade> historyGrades  = historyGradesDaoPort.findGradeByStudentId(2);

        assertTrue(mathGrades.iterator().hasNext(),
                "Student Service creates the grade: return true");
        assertTrue(scienceGrades.iterator().hasNext(),
                "Student Service creates the grade: return true");
        assertTrue(historyGrades.iterator().hasNext(),
                "Student Service creates the grade: return true");

    }

    @SqlGroup(
            {
                @Sql(scripts = "/insertData.sql",
                config = @SqlConfig(commentPrefix = "`")),
                @Sql("/overrideData.sql"),
                @Sql("/insertGrade.sql")
            })
    @Test
    void getGradeBookService() {

        GradeBook gradeBook = studentCore.getGradeBook();

        GradeBook gradeBookTest = new GradeBook();

        for (GradeBookCollegeStudent student : gradeBook.getStudents()) {
            if (student.getId() > 10) {
                gradeBookTest.getStudents().add(student);
            }
        }

        assertEquals(4, gradeBookTest.getStudents().size());
        assertTrue(gradeBookTest.getStudents().get(0).getStudentGrades().getHistoryGradeResults() != null);
        assertTrue(gradeBookTest.getStudents().get(0).getStudentGrades().getScienceGradeResults() != null);
        assertTrue(gradeBookTest.getStudents().get(0).getStudentGrades().getMathGradeResults() != null);
    }


    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
