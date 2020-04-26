package com.week1.reflection.annotation.core;

import com.company.Stopwatch;
import com.week1.reflection.annotation.models.TestInfoModel;
import com.week1.reflection.annotation.tests.TestExample;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TestProcessor {
    private HashMap<String, String> classes = new HashMap<String, String>();
    private HashMap<String, TestInfoModel> history = new HashMap<String, TestInfoModel>();
    private Stopwatch stopWatch = new Stopwatch();

    public TestProcessor() {
        this.classes.put("TestExample", "com.week1.reflection.annotation.tests.TestExample");
        this.classes.put("TestExample2", "com.week1.reflection.annotation.tests.TestExample2");

    }

    public void runTestsByClassName(String className) throws Exception {
        Reflections classesWithAnnotation  = new Reflections("com.week1.reflection.annotation.tests");
        Set<Class<?>> classes = classesWithAnnotation.getTypesAnnotatedWith(TesterInfo.class);
        Class<?> classToTest1 = null;
        for(Iterator<Class<?>> it = classes.iterator(); it.hasNext();){
            Class<?> classToFind = it.next();
            if(classToFind.getName().contains(className)){
                classToTest1 = classToFind;
               break;
            }
        }

        if (classToTest1 != null) {
            this.run(classToTest1);
        } else {
            throw new ClassNotFoundException("Class with name" + className + " not found;");
        }
    }

    public void getTestHistory() {
        StringBuilder builder = new StringBuilder();
        this.history.forEach((k, v) -> {
            builder.append(v.toString());
            builder.append(System.getProperty("line.separator"));
        });
        System.out.println(builder.toString());
    }

    public void getAlwaysPassedMethods() {
        StringBuilder builder = new StringBuilder();
        this.history.forEach((k, v) -> {
            if (v.getTimesFail() == 0 && v.getTimesPassed() != 0) {
                builder.append(v.toString());
                builder.append(System.getProperty("line.separator"));
            }

        });
        System.out.println(builder.toString());
    }

    public void getAlwaysFailedMethods() {
        StringBuilder builder = new StringBuilder();
        this.history.forEach((k, v) -> {
            if (v.getTimesFail() != 0 && v.getTimesRunning() != 0) {
                builder.append(v.toString());
                builder.append(System.getProperty("line.separator"));
            }

        });
        System.out.println(builder.toString());
    }

    public void getAllClassNames() {
        StringBuilder builder = new StringBuilder();
        this.classes.forEach((k, v) -> {
            builder.append(k);
            builder.append(System.getProperty("line.separator"));
        });
        System.out.println(builder.toString());
    }

    private void run(Class obj) {

        System.out.println("Testing...");

        if (obj.isAnnotationPresent(TesterInfo.class)) {

            Annotation annotation = obj.getAnnotation(TesterInfo.class);
            TesterInfo testerInfo = (TesterInfo) annotation;
            String className = obj.getName();

            for (Method method : obj.getDeclaredMethods()) {
                String methodName = method.getName();

                // if method is annotated with @Test
                if (method.isAnnotationPresent(Test.class)) {
                    TestInfoModel currentObj;
                    if (!this.history.containsKey(methodName)) {
                        this.history.put(method.getName(), new TestInfoModel(method.getName(), obj.getName()));
                    }
                    currentObj = history.get(method.getName());

                    Annotation annotation1 = method.getAnnotation(Test.class);
                    Test test = (Test) annotation1;

                    // if enabled = true (default)
                    if (test.enabled()) {
                        currentObj.incrementRunning();
                        try {
                            Stopwatch.start();
                            method.invoke(obj.getDeclaredConstructor().newInstance());
                            Stopwatch.stop();
                            currentObj.setBestTime(Stopwatch.elapsedTime());
                            currentObj.incrementPassing();
                            Stopwatch.reset();
                        } catch (Throwable ex) {
                            currentObj.incrementFailed();
                            Stopwatch.reset();
                        }
                    }
                }
            }
            System.out.println("Tested.");
        }
    }
}
