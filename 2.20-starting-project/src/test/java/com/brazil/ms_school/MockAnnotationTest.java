package com.brazil.ms_school;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import com.brazil.ms_school.app.core.ApplicationCore;
import com.brazil.ms_school.app.core.StudentGradesCore;
import com.brazil.ms_school.app.model.CollegeStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MsSchoolApplication.class)
class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGradesCore studentGradesCore;

    @MockBean // fornece a funcionalidade do mockito de mock mas tambem adiciona o bean fornecido ao contexto do aplicativo.
    private ApplicationAdapter applicationAdapter;

    @Autowired // determina que o bean sera injetado no contexto do aplicativo fornecido
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

    @DisplayName("Find Gpa")
    @Test
    void assertEqualsTestFindGpa() {
        when(applicationAdapter.findGradePointAverage(studentGradesCore.getMathGradeResults()))
                .thenReturn(88.31);

        assertEquals(88.31, applicationCore.findGradePointAverage(studentOne.getStudentGradesCore()
                .getMathGradeResults()));
    }

    @DisplayName("Not Null")
    @Test
    void testAssertNotNull() {
        when(applicationAdapter.checkNull(studentGradesCore.getMathGradeResults()))
                .thenReturn(true);

        assertNotNull(applicationCore.checkNull(studentOne.getStudentGradesCore()
                .getMathGradeResults()), "Object should not be null");
    }

    @DisplayName("Throw runtime error")
    @Test
    void throwRuntimeError() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        doThrow(new RuntimeException()).when(applicationAdapter).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> applicationCore.checkNull(nullStudent));

        verify(applicationAdapter, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        when(applicationAdapter.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");

        assertThrows(RuntimeException.class, () -> applicationCore.checkNull(nullStudent));

        assertEquals("Do not throw exception second time",
                applicationCore.checkNull(nullStudent));

        verify(applicationAdapter, times(2)).checkNull(nullStudent);
    }

}
