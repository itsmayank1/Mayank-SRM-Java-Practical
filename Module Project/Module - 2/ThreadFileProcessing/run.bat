@echo off
title Multi-Threaded CSV File Processor - SRM Full Stack Practical
echo Compiling MultiThreadedFileProcessor...
if not exist out mkdir out
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)
echo Running MultiThreadedFileProcessor...
java -cp out Main
pause
