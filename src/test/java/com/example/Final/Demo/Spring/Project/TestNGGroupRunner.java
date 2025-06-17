package com.example.Final.Demo.Spring.Project;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestNGGroupRunner {
    public static void main(String[] args) {
        List<String> groups = (args.length > 0) ? Arrays.asList(args[0].split(",")) : Collections.singletonList("sanity");
        TestNG testng = new TestNG();
        XmlSuite suite = new XmlSuite();
        suite.setName("Functional Group Suite");
        suite.setSuiteFiles(Collections.singletonList("testng.xml"));
        suite.setIncludedGroups(groups);
        testng.setXmlSuites(Collections.singletonList(suite));
        testng.run();
    }
}

