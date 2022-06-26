package com.brazil.luvtwocode;

import com.brazil.luvtwocode.domain.core.StudentAndGradeCore;
import com.brazil.luvtwocode.domain.model.CollegeStudent;
import com.brazil.luvtwocode.port.out.HistoryGradesDaoPort;
import com.brazil.luvtwocode.port.out.MathGradesDaoPort;
import com.brazil.luvtwocode.port.out.ScienceGradesDaoPort;
import com.brazil.luvtwocode.port.out.StudentDaoPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class GradeBookAdapterTest {

    private static MockHttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    private StudentAndGradeCore studentAndGradeCoreMock;

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CollegeStudent collegeStudent;

    public static final MediaType APPLICATION_JSON_UTF8 = APPLICATION_JSON;

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

    @BeforeAll
    static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Samuel");
        request.setParameter("lastname", "Hammond");
        request.setParameter("emailAddress", "samuel.hammond@luv2code.com");
    }

    @BeforeEach
    void setupDatabase() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @Test
    void getStudentsHttpRequest() throws Exception {
        collegeStudent.setFirstname("Paul");
        collegeStudent.setLastname("Hammond");
        entityManager.persist(collegeStudent);
        entityManager.flush();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void createStudentHttpRequest() throws Exception {
        collegeStudent.setFirstname("Paul");
        collegeStudent.setLastname("Hammond");
        collegeStudent.setEmailAddress("paul_hammond@luv2code.com");

        mockMvc.perform(post("/")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collegeStudent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)));

        CollegeStudent verifyStudent = studentDaoPort.findByEmailAddress("paul_hammond@luv2code.com");
        assertNotNull(verifyStudent, "Student should be valid");
    }

    @Test
    void deleteStudentHttpRequest() throws Exception {
        assertTrue(studentDaoPort.findById(1).isPresent());

        mockMvc.perform(delete("/student/{id}", 1))
                .andExpect(status().isNoContent());

        assertFalse(studentDaoPort.findById(1).isPresent());
    }

    @Test
    void deleteStudentHttpRequestErrorPage() throws Exception {
        assertFalse(studentDaoPort.findById(0).isPresent());

        mockMvc.perform(delete("/student/{id}", 0))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    void studentInformationHttpRequest() throws Exception {
        Optional<CollegeStudent> student = studentDaoPort.findById(1);

        assertTrue(student.isPresent());

        mockMvc.perform(get("/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Lon")))
                .andExpect(jsonPath("$.lastname", is("Hammond")))
                .andExpect(jsonPath("$.emailAddress", is("lon.hammond@luv2code_school.com")));
    }

    @Test
    void studentInformationHttpRequestEmptyResponse() throws Exception {
        Optional<CollegeStudent> student = studentDaoPort.findById(0);

        assertFalse(student.isPresent());

        mockMvc.perform(get("/studentInformation/{id}", 0))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    void createAValidGradeHttpRequest() throws Exception {
        this.mockMvc.perform(post("/grades")
                .contentType(APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "math")
                .param("studentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Lon")))
                .andExpect(jsonPath("$.lastname", is("Hammond")))
                .andExpect(jsonPath("$.emailAddress", is("lon.hammond@luv2code_school.com")))
                .andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(2)));
    }
    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
