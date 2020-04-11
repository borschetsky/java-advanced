package com.week1.reflection.annotation.tests;

import com.company.Math;
import com.week1.reflection.annotation.core.Test;
import com.week1.reflection.annotation.core.TesterInfo;

@TesterInfo(
        priority = TesterInfo.Priority.HIGH,
        createdBy = "Viacheslav MNoshkin",
        tags = {"infm205","test2" }
)
public class TestExample2 {
    @Test
    public void testE() {
        if (true)
            throw new RuntimeException("This test always failed");
    }

    @Test(enabled = false)
    public void testF() {
        if (false)
            throw new RuntimeException("This test always passed");
    }

    @Test(enabled = true)
    public void testG() {
        if (10 > 1) {
            // do nothing, this test always passed.
        }
    }

    @Test(enabled = true)
    public void testH() {
        int sum = Math.sum(5, 5);
        if(sum != 10) {
            throw  new RuntimeException("Test should return correct sum");
        }
    }
}
