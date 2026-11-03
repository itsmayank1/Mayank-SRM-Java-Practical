# Module Project - SRM Full Stack Practical

This repository contains the practical modules developed for the Java Full Stack curriculum.

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
├── Module - 3/
│   ├── employee-management-web/         (MAIN PROJECT: Employee Management System Web Application)
│   └── Additional Projects/             (Supplementary Spring Core & MVC Projects)
│       ├── dependency-injection-ioc/    (Spring Core XML-based IoC & DI)
│       ├── first-spring-app/            (Spring Core Annotation Context)
│       ├── request-mapping-controller/  (Spring Boot REST Web API Application)
│       └── spring-mvc/                  (Spring Web MVC WAR Application)
│
└── Module - 4/
    └── hospital-appointment-system/     (MAIN PROJECT: Hospital Appointment System JPA & Thymeleaf Web App)
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

### 🌟 Main Project: `employee-management-web`
**Employee Management System — Mini Project With Web Page**
- **Technologies**: Spring Boot 3.3.4, Spring MVC, Spring Security 6, Spring JWT (JJWT 0.12.6), Thymeleaf.
- **Data Persistence**: In-memory `ArrayList` (`CopyOnWriteArrayList`) with synchronized department relationships.
- **Security & JWT**: Custom login page (`/login`), role-based authorization (`ADMIN`, `MANAGER`, `USER`), and REST JWT token generator (`/api/auth/token`).
- **Web UI**: Interactive dashboard with payroll & department metrics, search/filter, and full CRUD.

```bash
cd "Module Project/Module - 3/employee-management-web"
mvn spring-boot:run
```
*Access via browser: `http://localhost:8080` (Default admin credentials: `hr` / `1234` or `admin` / `admin123`).*

---

### 📚 Additional Projects (`Additional Projects/`)

1. **`dependency-injection-ioc`**: Classic Spring XML configuration (`applicationContext.xml`), constructor/setter injection, bean lifecycle callbacks, and multiple messaging services.
2. **`first-spring-app`**: Annotation-driven Spring context using `@Configuration`, `@ComponentScan`, `@Service`, and `@Autowired`.
3. **`request-mapping-controller`**: Spring Boot RESTful Web API implementing `@RestController` and `@RequestMapping("/api/students")` with full CRUD operations.
4. **`spring-mvc`**: Traditional Spring Web MVC architecture with `DispatcherServlet`, `CalculatorWebController`, and interactive JSP views packaged into a WAR.

---

## Module - 4: Spring Data JPA & Thymeleaf Web Mini-Project

### 🏥 Main Project: `hospital-appointment-system`
**Hospital Appointment System — Mini Project With HTML/CSS/Thymeleaf**
- **Technologies**: Spring Boot 3.3.4, Spring Web, Spring Data JPA, Hibernate, MySQL Driver / Embedded H2, Thymeleaf.
- **Relational Model**:
  - `Department`: Specialized clinical units with head physicians (`@OneToMany` with Doctors).
  - `Doctor`: Specialist doctors with consulting days, hours, and department links.
  - `Patient`: Medical profiles with age, gender, blood group, contact, and address.
  - `Appointment`: Outpatient scheduling connecting Doctors and Patients with room assignments, consultation fees, and real-time status management.
- **Web Views**:
  - `index.html`: Executive dashboard with live KPI counters, today's schedule, and instant booking widget.
  - `departments.html`: Department directory, clinical wings, and doctor counts.
  - `doctors.html`: Specialist physician roster with search and department filtering.
  - `patients.html`: Patient registration and medical records directory.
  - `appointments.html`: Comprehensive appointment desk with filter by status/doctor/date and quick status toggles.
- **Automated Testing**: 12 unit and integration tests covering JPA repositories, entities, and Web controller endpoints with 100% pass rate.

```bash
cd "Module Project/Module - 4/hospital-appointment-system"
.\mvnw.cmd spring-boot:run
```
*Access via browser: `http://localhost:8080`*
