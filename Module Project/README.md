# Module Project - SRM Full Stack Practical

This repository contains the two core practical modules developed for the Java Full Stack curriculum.

## Structure

```
Module Project/
├── Module - 1/
│   └── Inventory Management System/     (Console Application)
└── Module - 2/
    ├── ThreadFileProcessing/            (Multi-Threaded CSV Processing Console Application)
    └── calculator-executable-jar/       (Swing GUI Calculator Maven Application)
```

---

## Module - 1: Inventory Management System (Console)
A menu-driven enterprise inventory catalog and billing management console application built in pure Java.

### Key Features:
- **Product & Category CRUD**: Automated ID generation, supplier tracking, and customizable reorder levels.
- **Advanced Search & Filtering**: Filter products by Category and by custom Price Range (`min` to `max`).
- **Product Sorting**: Multi-attribute sorting by Product Name (A-Z), Unit Price, and Stock Levels.
- **Stock Management & Alerts**: Restock recommendations for items at or below reorder threshold, and zero-stock monitor.
- **Point of Sale (POS) & Billing**: Enter customer name, product ID, and purchase quantity. System verifies stock, decrements inventory, calculates 18% GST, and prints a formatted receipt.
- **Valuation & Analytics**: Computes total asset inventory valuation ($\sum \text{price} \times \text{stock}$) and exports catalog to `inventory_export.csv`.

### How to Run:
```bash
cd "Module Project/Module - 1/Inventory Management System"
javac *.java
java Main
```
*(Or double-click `run.bat` on Windows)*

---

## Module - 2: Core Practical Applications

### 1. ThreadFileProcessing (Multi-Threaded CSV Processor)
A concurrent Java application demonstrating multi-threading, the Builder design pattern, and thread-safe data aggregation.

- **Features**:
  - Concurrently processes 6 monthly CSV datasets (`sales_january.csv` to `sales_june.csv`) using an `ExecutorService` thread pool.
  - Computes total revenue, physical units sold, average transaction size, and category market shares.
  - Benchmark performance timing reporting execution speed in milliseconds.
  - Dual export: Text summary (`report.txt`) and tabular CSV (`sales_summary.csv`).

#### How to Run:
```bash
cd "Module Project/Module - 2/ThreadFileProcessing"
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
java -cp out Main
```
*(Or double-click `run.bat` on Windows)*

---

### 2. calculator-executable-jar (GUI Calculator)
A Graphical User Interface (GUI) calculator built using Java Swing and packaged as a standalone executable JAR via `maven-jar-plugin:3.3.0`.

- **Features**:
  - Arithmetic operations: `ADD (+)`, `SUBTRACT (-)`, `MULTIPLY (*)`, `DIV (/)`.
  - Scientific functions: `MOD (%)`, `POW (x^y)`, `SQRT (√x)`, `PERCENT (%)`.
  - Controls: `CLEAR (C)`, `DEL (Backspace)`, `+/- (Sign Toggle)`, `EXIT`.
  - Scrollable real-time calculation history log.
  - Formatted integer/decimal presentation and zero-division safeguard.

#### How to Build & Run:
```bash
cd "Module Project/Module - 2/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```
*(Or double-click the `.jar` file in `target/` on Windows)*
