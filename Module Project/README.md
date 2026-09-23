# Module Projects - SRM Java Full Stack Practical

This section contains both core module practical projects:

## 1. `Module - 1/Inventory Management System` (Pure Console Application)
- **Form Factor**: Interactive Terminal / Console UI.
- **Core Focus**: Object-Oriented Programming (OOP) in Java, Collections Framework, File Persistence via Object Serialization, and Point of Sale (POS) Billing.
- **Key Enhancements**:
  - Full Product & Category CRUD.
  - Category and Price range filtering.
  - Multi-attribute sorting (Name, Price, Stock).
  - Restock and zero-stock recommendations.
  - Interactive billing invoice with automated 18% GST calculation.
  - Inventory financial valuation report and CSV / TXT data export.

### Execution:
```bash
cd "Module Project/Module - 1/Inventory Management System"
javac *.java
java Main
```
*(Or double-click `run.bat`)*

---

## 2. `Module - 2/MultiThreadedFileProcessor` (Pure Console Application)
- **Form Factor**: Multi-threaded High-Performance Console Engine.
- **Core Focus**: Java Multithreading (`ExecutorService`, `Callable`, `Future`), Builder Design Pattern, Thread Synchronization, Batch File I/O.
- **Key Enhancements**:
  - Expanded dataset: Q1 and Q2 monthly sales files (`sales_january.csv` through `sales_june.csv`).
  - Category breakdown & market share analytics.
  - Highest single deal / transaction analysis.
  - Millisecond execution benchmarking.
  - Dual export: Text executive summary (`report.txt`) and CSV summary (`sales_summary.csv`).

### Execution:
```bash
cd "Module Project/Module - 2/MultiThreadedFileProcessor"
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
java -cp out Main
```
*(Or double-click `run.bat`)*
