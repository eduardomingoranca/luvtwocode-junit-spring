package com.brazil.luvtwocode.demoproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.condition.JRE.*;
import static org.junit.jupiter.api.condition.OS.*;

class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved")
    void basicTest() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs(WINDOWS)
    void testForWindowsOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs(MAC)
    void testForMacOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs({MAC, WINDOWS})
    void testForMacAndWindowsOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnOs(LINUX)
    void testForLinuxOnly() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JAVA_11)
    void testForOnlyForJava11() {
        // execute method and perform asserts
    }

    @Test
    @EnabledOnJre(JAVA_13)
    void testForOnlyForJava13() {
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min= JAVA_10, max = JAVA_14)
    void testForOnlyForJavaRange() {
        // execute method and perform asserts
    }

    @Test
    @EnabledForJreRange(min=JAVA_9)
    void testForOnlyForJavaRangeMin() {
        // execute method and perform asserts
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LUV_TWO_CODE_ENV", matches = "DEV")
    void testOnlyForDevEnvironment() {
        // execute method and perform asserts
    }

    @Test
    @EnabledIfSystemProperty(named = "LUV_TWO_CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testOnlyForSystemProperty() {
        // execute method and perform asserts
    }

}

