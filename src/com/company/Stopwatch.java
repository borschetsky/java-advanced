package com.company;

public class Stopwatch {
    private static long start;
    private static double elapsedTime;

    public static void start(){
        start = System.currentTimeMillis();
    }

    public static void stop() {
        long now = System.currentTimeMillis();
        elapsedTime = (now - start) / 1000.0;
    }
    public static void reset() {
        elapsedTime = 0.0;
    }
    public static double elapsedTime() {

        return elapsedTime;
    }
}
