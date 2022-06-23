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

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

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
    
    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }
}
