package com.brazil.luvtwocode.demoproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

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


}
