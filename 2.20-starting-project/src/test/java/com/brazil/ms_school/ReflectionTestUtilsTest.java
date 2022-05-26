package com.brazil.ms_school;


import com.brazil.ms_school.app.core.StudentGradesCore;
import com.brazil.ms_school.app.model.CollegeStudent;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static java.util.Arrays.asList;

/*
  reflections utils -> acessa metodos e atributos/campos/variaveis privadas,
  permitindo obter e definir metodos e atributos/campos/variaveis privadas
*/
@SpringBootTest(classes = MsSchoolApplication.class)
class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGradesCore studentGradesCore;

    @BeforeEach
    void studentBeforeEach() {
        studentOne.setFirstname("Lon");
        studentOne.setLastname("Hammond");
        studentOne.setEmailAddress("lon.hammond@luv2code_school.com");
        studentOne.setStudentGradesCore(studentGradesCore);

        // definindo campos privados diretamente
        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGradesCore",
                new StudentGradesCore(new ArrayList<>(asList(
                        100.0, 85.0, 76.5, 91.75))));
    }


}
