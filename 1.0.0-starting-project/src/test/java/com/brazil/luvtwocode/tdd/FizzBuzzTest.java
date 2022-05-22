package com.brazil.luvtwocode.tdd;

import org.junit.jupiter.api.*;

import static com.brazil.luvtwocode.tdd.FizzBuzz.compute;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    // If number is divisible by 3, print Fizz
    // If number is divisible by 3 or 5, print FizzBuzz
    // If number is NOT divisible by 3 or 5, then print the number

    @DisplayName("Divisible by Three")
    @Test
    @Order(1)
    void testForDivisibleByThree() {
        String expected = "Fizz";

        assertEquals(expected, compute(3), "Should return Fizz");
    }

    // If number is divisible by 5, print Buzz

    @DisplayName("Divisible by Five")
    @Test
    @Order(2)
    void testForDivisibleByFive() {
        String expected = "Buzz";

        assertEquals(expected, compute(5), "Should return Buzz");
    }

}
