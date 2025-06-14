package com.example.Final.Demo.Spring.Project;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestResultViewer {
    public static void main(String[] args) {
        String fileName = "test-results.json";
        try (FileReader reader = new FileReader(fileName)) {
            List<Map<String, Object>> results = new Gson().fromJson(reader, new TypeToken<List<Map<String, Object>>>(){}.getType());
            int passed = 0, failed = 0, skipped = 0;
            System.out.println("Test Results Summary:\n");
            for (Map<String, Object> result : results) {
                String name = (String) result.get("method");
                String status = (String) result.get("status");
                Number duration = (Number) result.get("durationMs");
                System.out.printf("- %s: %s (%d ms)\n", name, status, duration.longValue());
                switch (status) {
                    case "PASSED": passed++; break;
                    case "FAILED": failed++; break;
                    case "SKIPPED": skipped++; break;
                }
            }
            System.out.println("\nTotal: " + results.size());
            System.out.println("Passed: " + passed);
            System.out.println("Failed: " + failed);
            System.out.println("Skipped: " + skipped);
        } catch (IOException e) {
            System.err.println("Could not read " + fileName + ": " + e.getMessage());
        }
    }
}
