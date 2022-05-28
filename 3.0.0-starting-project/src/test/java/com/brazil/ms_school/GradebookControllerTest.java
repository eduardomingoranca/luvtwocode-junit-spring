package com.brazil.ms_school;

import com.brazil.ms_school.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

// buscando as propriedades no arquivo application.properties
@TestPropertySource("/application.properties")
// configurando o mockmvc
@AutoConfigureMockMvc
@SpringBootTest
class GradebookControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("insert into student(id, email_address, firstname, lastname)" +
                "values (1, 'eric.roby@luv2code_school.com', 'Eric', 'Roby')");
    }
    
    @AfterEach
    void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
    }
}
