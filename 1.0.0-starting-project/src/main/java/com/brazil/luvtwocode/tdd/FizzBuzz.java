package com.brazil.luvtwocode.tdd;

public class FizzBuzz {

    public static String compute(int i) {

        // If number is divisible by 3 or 5, print FizzBuzz
        if ((i % 3 == 0) && (i % 5 == 0)) {
            return "FizzBuzz";
        }
        // If number is divisible by 3, print Fizz
        else if (i % 3 == 0) {
            return "Fizz";
        }
        // If number is divisible by 5, print Buzz
        else if (i % 5 == 0) {
            return "Buzz";
        }
        // If number is NOT divisible by 3 or 5, then print the number
        else {
            return Integer.toString(i);
        }
    }

}
