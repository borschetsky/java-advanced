package com.company;

import com.week1.reflection.annotation.core.TestProcessor;

import javax.swing.*;
import java.util.Scanner;

public class CommandProcessor {
    private static CommandProcessor SINGLE_INSTANCE = null;
    private Scanner scanner;
    private TestProcessor testProcessor;

    private  CommandProcessor() {
        scanner = new Scanner(System.in);
        testProcessor = new TestProcessor();
    }

    public  void Run() {
        System.out.println("Please enter a command: >");
        String in = scanner.nextLine();
        while(!in.equals("exit")) {
            if(in != null && !in.isEmpty()) {
                String[] cmdArgs = in.split(" ");
                if(cmdArgs[0].equals("testclass")) {
                    String classNameToTest;
                    try {
                        classNameToTest = cmdArgs[1];
                        try{
                            testProcessor.runTestsByClassName(cmdArgs[1]);
                        } catch ( Exception e) {
                            System.out.println(e.getMessage());;
                        }
                    } catch(Exception e) {
                        System.out.println("Please provide a classname to test...");
                    }
                } else if(cmdArgs[0].equals("getclassnames")) {
                    testProcessor.getAllClassNames();
                } else if(cmdArgs[0].equals("history")) {
                    testProcessor.getTestHistory();
                } else  if(cmdArgs[0].equals("getalwayspassedmethods")) {
                    testProcessor.getAlwaysPassedMethods();
                } else if( cmdArgs[0].equals("getalwaysfailedmethods")) {
                    testProcessor.getAlwaysFailedMethods();
                }
            } else {
                System.out.println("Please provide correct command");
            }
            in = scanner.nextLine();
        }
    }

    public  static  CommandProcessor getInstance() {
        synchronized (CommandProcessor.class) {
            if(SINGLE_INSTANCE == null) {
                SINGLE_INSTANCE = new CommandProcessor();
            }
        }
        return SINGLE_INSTANCE;
    }
}
