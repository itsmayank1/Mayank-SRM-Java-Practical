# Maven Projects - SRM Full Stack Practical

This repository section contains Maven-based Java applications developed for the practical coursework.

## Projects Overview

### 1. `calculator-executable-jar` (Swing GUI Calculator)
A full-featured Graphical User Interface (GUI) calculator built using Java Swing and packaged as a standalone executable JAR via `maven-jar-plugin`.

#### Key Features & Advancements:
- **Core Operations**: Addition (`+`), Subtraction (`-`), Multiplication (`*`), Division (`/`).
- **Advanced Scientific Functions**:
  - Modulo (`MOD %`)
  - Exponentiation / Power (`x^y`)
  - Square Root (`√x`)
  - Percentage Calculation (`%`)
  - Sign Inversion (`+/-`)
- **Interactive Editing & Controls**:
  - `CLEAR (C)`: Resets all input and result fields.
  - `DEL`: Backspace to delete the last entered digit.
  - `EXIT`: Closes the application.
- **Audit & Calculation History**: Live scrollable session calculation log.
- **Smart Formatting**: Displays whole numbers cleanly without trailing `.0` and manages decimal precision.
- **Robust Exception Handling**: Prevents division by zero, non-numeric strings, and square root of negative numbers.

#### Build & Run Commands:
```bash
cd "Maven Project/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```
*(Or simply double-click `calculator-executable-jar-1.0-SNAPSHOT.jar` in Windows File Explorer)*

---

### 2. `first-app` (Standard Maven Archetype Quickstart)
Demonstrates standard Maven project structure, configuration in `pom.xml`, lifecycle phases (`validate`, `compile`, `test`, `package`), and JUnit test execution.

#### Build & Run Commands:
```bash
cd "Maven Project/first-app"
mvn clean package
java -cp target/classes com.example.myapp.App
```
