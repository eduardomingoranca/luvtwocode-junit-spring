package com.brazil.luvtwocode.demoproject;

import org.junit.jupiter.api.*;

import java.util.List;

import static java.time.Duration.ofSeconds;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(MethodOrderer.MethodName.class)
//@TestMethodOrder(MethodOrderer.DisplayName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
    }

    @DisplayName("Multiply")
    @Test
    void testMultiply() {
        assertEquals(12, demoUtils.multiply(4, 3), "4*3 must be 12");
    }

    @Test
    // alterando o nome do metodo para um texto
    @DisplayName("Equals and Not Equals")
    @Order(1)
    void testEqualsAndNotEquals() {

        assertEquals(6, demoUtils.add(2, 4), "2+4 must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "1+9 must not be 6");
    }

    @Test
    @DisplayName("Null and Not Null")
    @Order(0)
    void testNullAndNotNull() {

        String str1 = null;
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(str1), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should be not null");
    }

    @DisplayName("Same and Not Same")
    @Test
    void testSameAndNotSame() {
        String str = "luv2code";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Objects should refer to same object");
        assertNotSame(str, demoUtils.getAcademy(), "Objects should not refer to same object");
    }

    @DisplayName("True and False")
    @Test
    @Order(30)
    void testTrueFalse() {
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "This should return false");
    }

    @DisplayName("Array Equals")
    @Test
    void testArrayEquals() {
        String[] stringArray = {"A", "B", "C"};

        // verificando que ambos os arrays de objetos sao iguais.
        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be the same");
    }

    @DisplayName("Iterable equals")
    @Test
    void testIterableEquals() {
        List<String> theList = of("luv", "2", "code");

        /**
         *  verificando que ambos os iteraveis de objeto sao iguais.
         *  Um iteravel (iterable) e uma instancia de uma classe que
         *  implementa a interface java.lang.Iterable interface.
         *
         *  Example: ArrayList, LinkedList, HashSet, TreeSet ...
         */
        assertIterableEquals(theList, demoUtils.getAcademyInList(), "Expected list should be same as actual list");
    }

    @DisplayName("Lines match")
    @Test
    @Order(50)
    void testLinesMatch() {
        List<String> theList = of("luv", "2", "code");

        // verificando se ambos as listas de strings correspondem.
        assertLinesMatch(theList, demoUtils.getAcademyInList(), "Lines should match");
    }

    @DisplayName("Throws and Does Not Throw")
    @Test
    void testThrowsAndDoesNotThrow() {

        assertThrows(Exception.class, () -> { demoUtils.throwException(-1);}, "Should throw exception");
        assertDoesNotThrow(() -> { demoUtils.throwException(5);}, "Should not throw exception");
    }

    @DisplayName("Timeout")
    @Test
    void testTimeout() {

        // verificando o tempo limite preventivamente.
        assertTimeoutPreemptively(ofSeconds(3), () -> { demoUtils.checkTimeout(); },
                "Method should execute in 3 seconds");
    }

}
