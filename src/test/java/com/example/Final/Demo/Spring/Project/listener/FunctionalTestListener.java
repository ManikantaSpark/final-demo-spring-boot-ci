package com.example.Final.Demo.Spring.Project.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class FunctionalTestListener implements ITestListener {
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("{ \"test\": \"" + result.getName() + "\", \"status\": \"PASSED\" }");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("{ \"test\": \"" + result.getName() + "\", \"status\": \"FAILED\" }");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("{ \"test\": \"" + result.getName() + "\", \"status\": \"SKIPPED\" }");
    }

    // Other methods can be left empty
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
