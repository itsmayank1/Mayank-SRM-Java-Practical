# Module Project - SRM Full Stack Practical

This repository contains the three core practical modules developed for the Java Full Stack curriculum.

## Structure

```
Module Project/
├── Module - 1/
│   └── Inventory Management System/     (Console Application)
│
├── Module - 2/
│   ├── ThreadFileProcessing/            (Multi-Threaded CSV Processing Console Application)
│   └── calculator-executable-jar/       (Swing GUI Calculator Maven Application)
│
└── Module - 3/
    ├── dependency-injection-ioc/        (Spring Core XML-based IoC & DI)
    ├── first-spring-app/                (Spring Core Annotation Context)
    ├── spring-mvc/                      (Spring Web MVC WAR Application)
    └── request-mapping-controller/      (Spring Boot REST Web API Application)
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

---

## Module - 2: Core Practical Applications

### 1. ThreadFileProcessing (Multi-Threaded CSV Processor)
- Concurrently processes 6 monthly CSV datasets (`sales_january.csv` to `sales_june.csv`) using an `ExecutorService` thread pool.
- Computes total revenue, physical units sold, average transaction size, and category market shares.
- Benchmark performance timing reporting execution speed in milliseconds.
- Dual export: Text summary (`report.txt`) and tabular CSV (`sales_summary.csv`).

```bash
cd "Module Project/Module - 2/ThreadFileProcessing"
javac -d out src\Main.java src\model\SalesRecord.java src\config\ProcessorConfig.java src\processor\CsvFileProcessor.java src\report\ReportAggregator.java
java -cp out Main
```

### 2. calculator-executable-jar (GUI Calculator)
- Arithmetic operations: `ADD (+)`, `SUBTRACT (-)`, `MULTIPLY (*)`, `DIV (/)`.
- Scientific functions: `MOD (%)`, `POW (x^y)`, `SQRT (√x)`, `PERCENT (%)`.
- Controls: `CLEAR (C)`, `DEL (Backspace)`, `+/- (Sign Toggle)`, `EXIT`.
- Scrollable real-time calculation history log.
- Packaged as a standalone executable JAR via `maven-jar-plugin:3.3.0`.

```bash
cd "Module Project/Module - 2/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```

---

## Module - 3: Spring Framework & Spring Boot

### 1. dependency-injection-ioc (XML-based IoC & DI)
Demonstrates classic Spring XML configuration (`applicationContext.xml`), constructor injection, setter injection, bean lifecycle callbacks, and scopes.

```bash
cd "Module Project/Module - 3/dependency-injection-ioc"
mvn compile exec:java
```

### 2. first-spring-app (Annotation-Driven Spring Context)
Demonstrates modern Spring annotation configuration without XML files using `@Configuration`, `@ComponentScan`, `@Service`, `@Component`, and `@Autowired`.

```bash
cd "Module Project/Module - 3/first-spring-app"
mvn compile exec:java
```

### 3. spring-mvc (Spring Web MVC)
Demonstrates standard Spring Web MVC architecture with `DispatcherServlet`, `CalculatorWebController`, and interactive JSP views packaged into a deployable WAR.

```bash
cd "Module Project/Module - 3/spring-mvc"
mvn clean package -DskipTests
```

### 4. request-mapping-controller (Spring Boot REST Web API)
A modern Spring Boot application created with Spring Initializr using `Spring Web` managing student records via REST endpoints.

```bash
cd "Module Project/Module - 3/request-mapping-controller"
mvn spring-boot:run
```
