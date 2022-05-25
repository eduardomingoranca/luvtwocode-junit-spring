package com.brazil.ms_school;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import com.brazil.ms_school.app.core.ApplicationCore;
import com.brazil.ms_school.app.core.StudentGradesCore;
import com.brazil.ms_school.app.model.CollegeStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @DisplayName("When & Verify")
    @Test
    void assertEqualsTestAddGrades() {
        when(applicationAdapter.addGradeResultsForSingleClass(studentGradesCore.getMathGradeResults()))
                .thenReturn(100.00);

        assertEquals(100, applicationCore.addGradeResultsForSingleClass(studentOne.getStudentGradesCore().getMathGradeResults()));

        // verifica se um determinado metodo foi chamado durante o processo de teste
        verify(applicationAdapter).addGradeResultsForSingleClass(studentGradesCore.getMathGradeResults());

        // verifica a quantidade de vezes que o metodo foi chamado durante o processo de teste
        verify(applicationAdapter, times(1)).addGradeResultsForSingleClass(studentGradesCore.getMathGradeResults());
    }

}
