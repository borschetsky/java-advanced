package com.company;

import com.week1.reflection.annotation.core.TestProcessor;

public class Main {

    public static void main(String[] args) {
        // write your code here
//        RunTestsTest.run();
        TestProcessor processor = new TestProcessor();

        try {
            processor.runTestsByClassName("TestExample");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
