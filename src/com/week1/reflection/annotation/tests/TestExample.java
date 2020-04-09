package com.week1.reflection.annotation.tests;

import com.company.Math;
import com.week1.reflection.annotation.core.Test;
import com.week1.reflection.annotation.core.TesterInfo;
import com.week1.reflection.annotation.core.TesterInfo.Priority;

@TesterInfo(
        priority = Priority.HIGH,
        createdBy = "Viacheslav MNoshkin",
        tags = {"infm205","test" }
)
public class TestExample {

    @Test
    void testA() {
        if (true)
            throw new RuntimeException("This test always failed");
    }

    @Test(enabled = false)
    void testB() {
        if (false)
            throw new RuntimeException("This test always passed");
    }

    @Test(enabled = true)
    void testC() {
        if (10 > 1) {
            // do nothing, this test always passed.
        }
    }

    @Test(enabled = true)
    void testD() {
        int sum = Math.sum(5, 5);
        if(sum != 10) {
            throw  new RuntimeException("Test should return correct sum");
        }
    }

}