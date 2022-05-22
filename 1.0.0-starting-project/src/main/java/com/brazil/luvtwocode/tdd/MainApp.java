package com.brazil.luvtwocode.tdd;

import static com.brazil.luvtwocode.tdd.FizzBuzz.compute;

public class MainApp {

    public static void main(String[] args) {

        for (int i = 0; i <= 100; i++) {
//            System.out.println(i + "," + compute(i));
            System.out.println(compute(i));
        }
    }
}
