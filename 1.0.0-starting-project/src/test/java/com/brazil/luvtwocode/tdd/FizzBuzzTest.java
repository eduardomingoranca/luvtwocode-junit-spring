package com.brazil.luvtwocode.tdd;

import org.junit.jupiter.api.*;

import static com.brazil.luvtwocode.tdd.FizzBuzz.compute;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    // If number is divisible by 3, print Fizz

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

    // If number is divisible by 3 and 5, print FizzBuzz

    @DisplayName("Divisible by Three and Five")
    @Test
    @Order(3)
    void testForDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";

        assertEquals(expected, compute(15), "Should return FizzBuzz");
    }

    // If number is NOT divisible by 3 or 5, then print the number

    @DisplayName("Not Divisible by Three or Five")
    @Test
    @Order(4)
    void testForNotDivisibleByThreeOrFive() {
        String expected = "1";

        assertEquals(expected, compute(1), "Should return 1");
    }

}
