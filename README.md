# SRM Institute of Science and Technology - CSE Core
## Java Full Stack Practical Mini-Projects

**Student Name**: Mayank Upadhayay  
**Email**: mayankupadhayay2020115@gmail.com  
**Course**: Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## Repository Architecture & Directory Layout

This repository contains all enhanced mini-projects completed for the Java Full Stack Practical curriculum. Every project preserves its authentic architecture (GUI vs. Console) while incorporating advanced production-grade features, error handling, and unit test suites.

```
Mayank-SRM-Java-Practical/
├── Maven Project/
│   ├── calculator-executable-jar/       [GUI Application]
│   │   ├── pom.xml                      (Configured with maven-jar-plugin for executable JAR)
│   │   └── src/main/java/com/example/myapp/App.java (Swing Scientific Calculator)
│   ├── first-app/                       [Console Application]
│   │   ├── pom.xml
│   │   └── src/main/java/com/example/myapp/App.java
│   └── README.md
│
├── Module Project/
│   ├── Module - 1/
│   │   └── Inventory Management System/ [Console Application]
│   │       ├── Main.java                (Interactive Scanner CLI Menu)
│   │       ├── Product.java             (POJO with reorder levels and pricing)
│   │       ├── Category.java            (Categorization model)
│   │       ├── InventoryManager.java    (CRUD, sorting, POS billing, valuation)
│   │       ├── FileManager.java         (Binary serialization & CSV export)
│   │       ├── InventoryTest.java       (Automated test verification)
│   │       ├── run.bat                  (One-click execution script)
│   │       └── README.md
│   ├── Module - 2/
│   │   └── MultiThreadedFileProcessor/  [Console Application]
│   │       ├── src/                     (Multithreaded ExecutorService batch processor)
│   │       ├── data/                    (Q1 & Q2 monthly CSV sales data files)
│   │       ├── run.bat                  (One-click execution script)
│   │       └── README.md
│   └── README.md
│
├── Spring Core/
│   ├── dependency-injection-ioc/        [Spring IoC Container]
│   │   ├── src/main/resources/applicationContext.xml (Setter & Constructor Injection, Scopes)
│   │   ├── src/main/java/com/example/   (MessageService, Email, SMS, WhatsApp, Printer)
│   │   └── pom.xml
│   ├── first-spring-app/                [Annotation-based Spring Context]
│   │   ├── src/main/java/com/example/App.java (@Configuration, @ComponentScan, @Service, @Autowired)
│   │   └── pom.xml
│   ├── spring-mvc/                      [Spring Web MVC Application]
│   │   ├── src/main/java/com/example/controller/CalculatorWebController.java
│   │   ├── src/main/webapp/WEB-INF/web.xml (DispatcherServlet registration)
│   │   ├── src/main/webapp/WEB-INF/views/calculator.jsp
│   │   ├── src/main/webapp/index.jsp
│   │   └── pom.xml                      (Packaged as deployable WAR)
│   └── README.md
│
└── README.md                            (Project documentation and run guides)
```

---

## 1. Maven Project: Executable Calculator [GUI]
* **Form Factor**: Java Swing Graphical User Interface (`JFrame`, `JPanel`, `JButton`, `JTextField`, `JLabel`, `JScrollPane`).
* **Packaging**: Single standalone executable `.jar` file built with `org.apache.maven.plugins:maven-jar-plugin:3.3.0`.
* **Features**:
  * Arithmetic operations: `ADD (+)`, `SUBTRACT (-)`, `MULTIPLY (*)`, `DIV (/)`.
  * Scientific functions: `MOD (%)`, `POW (x^y)`, `SQRT (√x)`, `PERCENT (%)`.
  * Editing & controls: `CLEAR (C)`, `DEL (Backspace)`, `+/- (Sign Toggle)`, `EXIT`.
  * Real-time calculation audit log / history stream.
  * Formatted integer/floating-point representation and zero-division safeguard.

### Quick Run:
```bash
cd "Maven Project/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```

---

## 2. Module Project - 1: Inventory Management System [Console]
* **Form Factor**: Pure Console CLI (`Scanner`, formatted ASCII tables).
* **Persistence**: Java Object Serialization (`products.dat`, `categories.dat`).
* **Features**:
  * Product and Category CRUD with unique automated IDs.
  * Filter by category and user-defined price ranges.
  * Multi-field sorting (Alphabetical A-Z, Price Low-High / High-Low, Stock levels).
  * Point of Sale (POS) billing: decrements inventory stock, calculates 18% GST, and prints a formatted tax receipt.
  * Inventory valuation analytics ($\sum \text{price} \times \text{stock}$) and export to `inventory_export.csv` and `inventory_summary.txt`.

### Quick Run:
```bash
cd "Module Project/Module - 1/Inventory Management System"
javac *.java
java Main
```

---

## 3. Module Project - 2: Multi-Threaded CSV File Processor [Console]
* **Form Factor**: Multi-threaded High-Throughput Console Engine.
* **Concurrency**: `java.util.concurrent.ExecutorService`, `Callable<List<SalesRecord>>`, `Future<T>`, and thread-safe synchronizers.
* **Features**:
  * Batch processes 6 monthly sales datasets concurrently across Q1 and Q2 (`sales_january.csv` to `sales_june.csv`).
  * Computes total revenue, quantity, average transaction value, leading product, and category market share percentages.
  * Thread pool benchmark reporting millisecond execution speeds.
  * Dual output: Formatted text report (`report.txt`) and tabular CSV (`sales_summary.csv`).

### Quick Run:
```bash
cd "Module Project/Module - 2/MultiThreadedFileProcessor"
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
java -cp out Main
```

---

## 4. Spring Core Projects
* **`dependency-injection-ioc`**:
  * Demonstrates XML bean definition (`applicationContext.xml`), `MessageService` abstractions (`EmailMessageService`, `SMSMessageService`, `WhatsAppMessageService`), both Setter and Constructor Injection, singleton vs prototype scopes, and bean lifecycle callbacks (`init-method`, `destroy-method`).
  * Run: `mvn compile exec:java`
* **`first-spring-app`**:
  * Demonstrates modern annotation-driven Spring configuration with `@Configuration`, `@ComponentScan`, `@Service`, `@Component`, and `@Autowired` constructor injection across layered services.
  * Run: `mvn compile exec:java`
* **`spring-mvc`**:
  * Demonstrates Jakarta/Spring Web MVC architecture with `DispatcherServlet`, `CalculatorWebController`, and JSP views (`calculator.jsp`, `index.jsp`) packaged as a deployable WAR.
  * Build: `mvn clean package`

---

## Verification & Automated Test Status

| Module | Verification Method | Status |
|---|---|---|
| **Calculator GUI** | `mvn clean test package` | PASS (100% Tests Passed, Executable JAR built) |
| **First Maven App** | `mvn clean test package` | PASS (100% Tests Passed) |
| **Inventory System** | `InventoryTest.java` (6 Unit Flows) | PASS (CRUD, Stock, POS Billing, CSV Export) |
| **File Processor** | 6-thread concurrent execution benchmark | PASS (49 records parsed, 12 ms duration) |
| **Spring IoC & DI** | `mvn clean test exec:java` | PASS (Context loaded, beans injected, scopes verified) |
| **First Spring App** | `mvn clean test exec:java` | PASS (Component scan & autowiring verified) |
| **Spring MVC** | `mvn clean package` | PASS (WAR built successfully) |
