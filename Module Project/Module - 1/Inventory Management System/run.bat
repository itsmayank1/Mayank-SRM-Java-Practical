@echo off
title Inventory Management System - SRM Full Stack Practical
echo Compiling Inventory Management System...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)
echo Starting Inventory Management System...
java Main
pause
