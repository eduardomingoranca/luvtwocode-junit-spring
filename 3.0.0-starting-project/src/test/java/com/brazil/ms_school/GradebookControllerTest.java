package com.brazil.ms_school;

import com.brazil.ms_school.models.CollegeStudent;
import com.brazil.ms_school.models.GradebookCollegeStudent;
import com.brazil.ms_school.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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
class GradebookControllerTest {

    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    @BeforeAll
    static void setup() {
        /*
          fazendo uma solicitacao de simulada, podendo preencher
          esta solicitacao com alguns dados.
         */
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Chad");
        request.setParameter("lastname", "Darby");
        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("insert into student(id, email_address, firstname, lastname)" +
                "values (1, 'eric.roby@luv2code_school.com', 'Eric', 'Roby')");
    }

    @Test
    void getStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
                "eric_roby@luv2code_school.com");

        CollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby",
                "chad_darby@luv2code_school.com");

        List<CollegeStudent> collegeStudentList = new ArrayList<>(asList(studentOne, studentTwo));

        when(studentAndGradeServiceMock.getGradebook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradebook());

        // realizando uma solicitacao web
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "index");
    }

    @Test
    void createStudentHttpRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("firstname", request.getParameterValues("firstname")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        assertViewName(mav, "index");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
    }
}
