package com.week1.reflection.annotation.core;

import com.company.Stopwatch;
import com.week1.reflection.annotation.tests.TestExample;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.HashMap;

public class TestProcessor {
    private HashMap<String, String> classes = new HashMap<String, String>();

    public  TestProcessor() {
        this.classes.put("TestExample", "com.week1.reflection.annotation.tests.TestExample");
        this.classes.put("TestExample2", "com.week1.reflection.annotation.tests.TestExample2");

    }

    public void runTestsByClassName (String className) throws Exception{
        if(this.classes.containsKey(className)) {
            System.out.println("Testing...");
            String classFullPath = this.classes.get(className);
            Class<?> classToTest = Class.forName(classFullPath);
            this.run(classToTest);
        } else {
            throw new ClassNotFoundException("Class with name" + className + " not found;");
        }
    }

     private void run( Class obj) {

        System.out.println("Testing...");

        int passed = 0, failed = 0, count = 0, ignore = 0;

//        Class<TestExample> obj = TestExample.class;

        // Process @TesterInfo
        if (obj.isAnnotationPresent(TesterInfo.class)) {

            Annotation annotation = obj.getAnnotation(TesterInfo.class);
            TesterInfo testerInfo = (TesterInfo) annotation;

            System.out.printf("%nPriority :%s", testerInfo.priority());
            System.out.printf("%nCreatedBy :%s", testerInfo.createdBy());
            System.out.printf("%nTags :");

            int tagLength = testerInfo.tags().length;
            for (String tag : testerInfo.tags()) {
                if (tagLength > 1) {
                    System.out.print(tag + ", ");
                } else {
                    System.out.print(tag);
                }
                tagLength--;
            }

            System.out.printf("%nLastModified :%s%n%n", testerInfo.lastModified());
            for (Method method : obj.getDeclaredMethods()) {

                // if method is annotated with @Test
                if (method.isAnnotationPresent(Test.class)) {

                    Annotation annotation1 = method.getAnnotation(Test.class);
                    Test test = (Test) annotation1;

                    // if enabled = true (default)
                    if (test.enabled()) {

                        try {
                            Stopwatch.start();
                            method.invoke(obj);
                            Stopwatch.stop();
                            System.out.printf("%s - Test '%s' - passed %n", ++count, method.getName());
                            passed++;
                        } catch (Throwable ex) {
                            System.out.printf("%s - Test '%s' - failed: %s %n", ++count, method.getName(), ex.getCause());
                            failed++;
                        }

                    } else {
                        System.out.printf("%s - Test '%s' - ignored%n", ++count, method.getName());
                        ignore++;
                    }

                }

            }
            System.out.printf("%nResult : Total : %d, Passed: %d, Failed %d, Ignore %d%n", count, passed, failed, ignore);
        }
    }
}
