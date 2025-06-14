package com.example.Final.Demo.Spring.Project.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestResultLogger implements ITestListener {
    private static final String FILE_NAME = "target/test-results.json";
    private List<String> results = new ArrayList<>();
    private FileWriter writer;

    @Override
    public void onStart(ITestContext context) {
        try {
            writer = new FileWriter(FILE_NAME, false);
            writer.write("[\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // No-op
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logResult(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logResult(result, "FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logResult(result, "SKIPPED");
    }

    private void logResult(ITestResult result, String status) {
        long duration = System.currentTimeMillis() - result.getStartMillis();
        String entry = String.format(
            "  {\"method\":\"%s\",\"status\":\"%s\",\"durationMs\":%d}",
            result.getMethod().getMethodName(), status, duration
        );
        results.add(entry);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            for (int i = 0; i < results.size(); i++) {
                writer.write(results.get(i));
                if (i < results.size() - 1) {
                    writer.write(",\n");
                }
            }
            writer.write("\n]");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
