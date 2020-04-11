package com.week1.reflection.annotation.models;

public class TestInfoModel {
    String methodName;
    String parentClass;
    int timesRunning;
    int timesFail;
    int timesPassed;
    double bestTime;

    public TestInfoModel(String methodName, String parentClass) {
        this.methodName = methodName;
        this.parentClass = parentClass;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setBestTime(double bestTime) {
        if(this.bestTime > bestTime) {
            this.bestTime = bestTime;
        }
    }

    public double getBestTime() {
        return  this.bestTime;
    }

    public int getTimesFail() {
        return this.timesFail;
    }

    public  int getTimesPassed() {
        return  this.timesPassed;
    }

    public  int getTimesRunning() {
        return  this.timesRunning;
    }

    public void incrementPassing() {
        this.timesPassed++;
    }

    public void incrementFailed() {
        this.timesFail++;
    }

    public void incrementRunning() {
        this.timesRunning++;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Method name: " + methodName+
                ", Times Failed: " + timesFail + ", Times passed: " +
                timesPassed + ", Times run: " + timesRunning + ", Best runnig time: " + bestTime);
        return  builder.toString();
    }
}
