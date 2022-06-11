package com.brazil.ms_school;

import com.brazil.ms_school.app.domain.core.StudentAndGradeCore;
import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.domain.model.GradeBookCollegeStudent;
import com.brazil.ms_school.app.port.out.StudentRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// buscando as propriedades no arquivo application.properties
@TestPropertySource("/application.properties")
// configurando o mockmvc
@AutoConfigureMockMvc
@SpringBootTest
class GradeBookAdapterTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeCore studentCreateCoreMock;

    @Autowired
    private StudentRepositoryPort studentRepositoryPort;

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

    @BeforeAll
    static void setup() {
        /*
          fazendo uma solicitacao de simulada, podendo preencher
          esta solicitacao com alguns dados.
         */
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Issac");
        request.setParameter("lastname", "Cohen");
        request.setParameter("emailAddress", "isaac.cohen@luv2code_school.com");
    }

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @Test
    void getStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradeBookCollegeStudent("Joseph", "Mizrahi",
                "joseph_mizrahi@luv2code_school.com");

        CollegeStudent studentTwo = new GradeBookCollegeStudent("Isaac", "Cohen",
                "isaac_cohen@luv2code_school.com");

        List<CollegeStudent> collegeStudentList = new ArrayList<>(asList(studentOne, studentTwo));

        when(studentCreateCoreMock.getGradeBook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentCreateCoreMock.getGradeBook());

        // realizando uma solicitacao web
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "index");
    }

    @Test
    void createStudentHttpRequest() throws Exception {

        CollegeStudent studentOne = new CollegeStudent("Joseph", "Mizrahi",
                "joseph_mizrahi@luv2code_school.com");

        List<CollegeStudent> collegeStudentList = new ArrayList<>(asList(studentOne));

        when(studentCreateCoreMock.getGradeBook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentCreateCoreMock.getGradeBook());

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "index");

        // buscando o aluno pelo endereco de email
        CollegeStudent verifyStudent = studentRepositoryPort
                .findByEmailAddress("isaac.cohen@luv2code_school.com");

        // verificando se o aluno existe
        assertNotNull(verifyStudent, "Student should be found");
    }

    @Test
    void deleteStudentHttpRequest() throws Exception {
        // verificando se o aluno
        assertTrue(studentRepositoryPort.findById(1).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/student/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "index");

        assertFalse(studentRepositoryPort.findById(1).isPresent());
    }

    @Test
    void deleteStudentHttpRequestErrorPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/student/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "error");
    }

    @Test
    void studentInformationHttpRequest() throws Exception {
        assertTrue(studentRepositoryPort.findById(1).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/studentInformation/{id}", 1))
                        .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "studentInformation");
    }

    @Test
    void studentInformationHttpStudentDoesNotExistRequest() throws Exception {
        assertFalse(studentRepositoryPort.findById(0).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/studentInformation/{id}", 0))
                        .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "error");
    }

    @Test
    void createValidGradeHttpRequest() throws Exception {
        assertTrue(studentRepositoryPort.findById(1).isPresent());

        GradeBookCollegeStudent student = studentCore.studentInformation(1);

        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/grades")
                .contentType(APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "math")
                .param("studentId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "studentInformation");

        student = studentCore.studentInformation(1);

        assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    void createAValidGradeHttpRequestStudentDoesNotExistEmptyResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/grades")
                        .contentType(APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "history")
                        .param("studentId", "0"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "error");
    }

    @Test
    void createANonValidGradeHttpRequestGradeTypeDoesNotExistEmptyResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/grades")
                        .contentType(APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "literature")
                        .param("studentId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "error");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }
}
