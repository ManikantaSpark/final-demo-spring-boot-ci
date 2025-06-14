@echo off
mvn compile exec:java -Dexec.mainClass=com.example.Final.Demo.Spring.Project.TestNGGroupRunner -Dexec.args="order"
pause
