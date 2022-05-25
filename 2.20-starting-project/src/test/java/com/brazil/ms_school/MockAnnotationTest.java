package com.brazil.ms_school;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import com.brazil.ms_school.app.core.ApplicationCore;
import com.brazil.ms_school.app.core.StudentGradesCore;
import com.brazil.ms_school.app.model.CollegeStudent;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = MsSchoolApplication.class)
class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGradesCore studentGradesCore;

    @Mock
    private ApplicationAdapter applicationAdapter;

    @InjectMocks
    private ApplicationCore applicationCore;

    @BeforeEach
    void beforeEach() {
        studentOne.setFirstname("Lon");
        studentOne.setLastname("Hammond");
        studentOne.setEmailAddress("lon.hammond@luv2code_school.com");
        studentOne.setStudentGradesCore(studentGradesCore);
    }

    
}
