package com.ctypists.tankstars;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


// JUNit Runner Class
public class Runner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Tests.class);
        System.out.println("Tests run: " + result.getRunCount());
        System.out.println("Tests failed: " + result.getFailureCount());
    }
}