# SRM Institute of Science and Technology - CSE Core
## Java Full Stack Practical Mini-Projects

**Student Name**: Mayank Upadhyay  
**Email**: mayankupadhayay2020115@gmail.com  
**Course**: Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## Repository Structure

```
Module Project/
├── Module - 1/
│   └── Inventory Management System/     (Console-based Inventory & POS Billing)
└── Module - 2/
    ├── ThreadFileProcessing/            (Multi-Threaded CSV Processing Engine)
    └── calculator-executable-jar/       (Swing GUI Calculator Executable JAR)
```

---

## Overview of Modules

### Module - 1: Inventory Management System (Console)
A menu-driven enterprise inventory catalog and billing management console application built in pure Java with serialization persistence.

- **Product & Category CRUD**: Automated ID assignment, supplier tracking, and reorder threshold levels.
- **Search & Filter**: Search by keyword, filter by category, and search by price range (`min` to `max`).
- **Sorting Engine**: Sort products by Name (A-Z), Price (Low-High / High-Low), and Stock quantity.
- **Point of Sale (POS) Billing**: Interactive sales transaction that decrements inventory stock, calculates 18% GST, and prints a formatted receipt.
- **Valuation & Analytics**: Computes total asset inventory valuation ($\sum \text{price} \times \text{stock}$) and exports catalog to `inventory_export.csv`.

**Run Command**:
```bash
cd "Module Project/Module - 1/Inventory Management System"
javac *.java
java Main
```
*(Or double-click `run.bat`)*

---

### Module - 2: Core Practical Applications

#### 1. ThreadFileProcessing (Multi-Threaded CSV Processor)
A concurrent Java application demonstrating multi-threading, the Builder pattern, and thread-safe data aggregation.

- **Concurrency**: Parallel processing of 6 monthly CSV datasets (`sales_january.csv` to `sales_june.csv`) using an `ExecutorService` thread pool.
- **Analytics**: Calculates total revenue, physical units sold, average transaction size, and category market shares.
- **Performance Benchmark**: High-precision timer logging thread completion in milliseconds.
- **Dual Export**: Generates both an executive summary (`report.txt`) and tabular CSV (`sales_summary.csv`).

**Run Command**:
```bash
cd "Module Project/Module - 2/ThreadFileProcessing"
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
java -cp out Main
```
*(Or double-click `run.bat`)*

---

#### 2. calculator-executable-jar (GUI Calculator)
A Graphical User Interface (GUI) calculator built with Java Swing and packaged as a standalone executable JAR via `maven-jar-plugin:3.3.0`.

- **Operations**: `ADD (+)`, `SUBTRACT (-)`, `MULTIPLY (*)`, `DIV (/)`.
- **Scientific Functions**: `MOD (%)`, `POW (x^y)`, `SQRT (√x)`, `PERCENT (%)`.
- **Controls**: `CLEAR (C)`, `DEL (Backspace)`, `+/- (Sign Toggle)`, `EXIT`.
- **History Log**: Scrollable real-time calculation audit log.
- **Clean Number Formatting**: Integer results display cleanly without redundant decimals, with zero-division protection.

**Build & Run Command**:
```bash
cd "Module Project/Module - 2/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```
*(Or double-click the `.jar` file in `target/`)*
