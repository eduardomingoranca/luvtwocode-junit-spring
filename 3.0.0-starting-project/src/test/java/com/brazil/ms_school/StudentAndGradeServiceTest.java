package com.brazil.ms_school;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/*
 permite carregar propriedades durante o teste, neste caso
 sera referenciado a application.properties.
 */
@TestPropertySource("/application.properties")
@SpringBootTest
class StudentAndGradeServiceTest {

    @Test
    void createStudentService() {
        
    }
}
