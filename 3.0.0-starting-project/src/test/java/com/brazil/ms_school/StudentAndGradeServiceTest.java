package com.brazil.ms_school;

import com.brazil.ms_school.service.StudentAndGradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/*
 permite carregar propriedades durante o teste, neste caso
 sera referenciado a application.properties.
 */
@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;

    @Test
    void createStudentService() {
        studentService.createStudent("Chad", "Darby",
                "chad.darby@luv2code_school.com");
    }
}
