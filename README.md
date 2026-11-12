# SRM Institute of Science and Technology - CSE Core
## Java Full Stack Practical Mini-Projects

**Student Name**: Mayank Upadhyay  
**Email**: mayankupadhayay2020115@gmail.com  
**Course**: Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## Repository Structure

```
Mayank-SRM-Java-Practical/
├── Module Project/
│   ├── Module - 1/
│   │   └── Inventory Management System/     (Console-based Inventory & POS Billing)
│   │
│   ├── Module - 2/
│   │   ├── ThreadFileProcessing/            (Multi-Threaded CSV Processing Engine)
│   │   └── calculator-executable-jar/       (Swing GUI Calculator Executable JAR)
│   │
│   ├── Module - 3/
│   │   ├── employee-management-web/         (MAIN PROJECT: Employee Management Web App)
│   │   └── Additional Projects/             (Supplementary Spring Core & MVC Projects)
│   │       ├── dependency-injection-ioc/    (Spring Core XML-based IoC & DI)
│   │       ├── first-spring-app/            (Spring Core Annotation Context)
│   │       ├── request-mapping-controller/  (Spring Boot REST Web Controller)
│   │       └── spring-mvc/                  (Spring Web MVC WAR Application)
│   │
│   ├── Module - 4/
│   │   └── employee-management-react/       (MAIN PROJECT: React 19 + Vite SPA with Spring Boot REST Integration)
│   │       └── employee-management-react-springboot/
│   │           └── employee-management-react-frontend/
│   │               ├── src/                 (React Components, Pages, Context, Styles)
│   │               └── backend-integration/ (Spring Boot REST Replacement Controllers & Security)
│   │
│   └── Module - 5/
│       └── hospital-appointment-system/     (MAIN PROJECT: Hospital Appointment System JPA & Thymeleaf)
│
└── Assignment Project/
    └── Assignment - 1/
        └── spring-boot-web-project/         (Spring Boot 5-Page Static Website)
```

---

## Detailed Module Documentation

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

#### 2. calculator-executable-jar (GUI Calculator)
A Graphical User Interface (GUI) calculator built with Java Swing and packaged as a standalone executable JAR via `maven-jar-plugin:3.3.0`.

- **Operations**: `ADD (+)`, `SUBTRACT (-)`, `MULTIPLY (*)`, `DIV (/)`.
- **Scientific Functions**: `MOD (%)`, `POW (x^y)`, `SQRT (√x)`, `PERCENT (%)`.
- **Controls**: `CLEAR (C)`, `DEL (Backspace)`, `+/- (Sign Toggle)`, `EXIT`.
- **History Log**: Scrollable real-time calculation audit log.

**Build & Run Command**:
```bash
cd "Module Project/Module - 2/calculator-executable-jar"
mvn clean package
java -jar target/calculator-executable-jar-1.0-SNAPSHOT.jar
```

---

### Module - 3: Spring Framework & Spring Boot

#### 🌟 Main Project: `employee-management-web`
**Employee Management System — Mini Project With Web Page**
- **Technologies**: Spring Boot 3.3.4, Spring MVC, Spring Security 6, Spring JWT (JJWT 0.12.6), Thymeleaf.
- **Data Persistence**: In-memory `ArrayList` (`CopyOnWriteArrayList`) with synchronized department relationships.
- **Security & JWT**: Custom login page (`/login`), role-based authorization (`ADMIN`, `MANAGER`, `USER`), and REST JWT token generator (`/api/auth/token`).
- **Web UI**: Interactive dashboard with payroll & department metrics, search/filter, and full CRUD.

**Run Command**:
```bash
cd "Module Project/Module - 3/employee-management-web"
mvn spring-boot:run
```
*Access via browser: `http://localhost:8080` (Default credentials: `hr` / `1234`, `manager` / `1234`, `employee` / `1234`).*

#### 📚 Additional Projects (`Additional Projects/`)
- `dependency-injection-ioc`: XML-based IoC & DI with lifecycle callbacks.
- `first-spring-app`: Pure annotation-driven Spring context.
- `request-mapping-controller`: Spring Boot RESTful Web API with Student CRUD.
- `spring-mvc`: Standard DispatcherServlet and JSP views.

---

### Module - 4: Employee Management System (React Frontend + Spring Boot REST)

#### ⚛️ Main Project: `employee-management-react`
**Single Page Application (SPA) with React 19, Vite, and Spring Boot REST API Integration**
- **Frontend Stack**: React 19, Vite, React Router v7 (`react-router-dom`), Vanilla CSS.
- **Role-Based Access**:
  - `HR`: View, create, update, and delete employees; create new departments.
  - `Manager`: View and update employee details.
  - `Employee`: View employee and department directories.
- **REST Integration**: Pure JSON REST controllers replacing server-rendered Thymeleaf templates (`/api/auth/**`, `/api/employees/**`, `/api/departments/**`).
- **Seamless SPA Routing**: `ReactForwardController` handles direct deep-linking and browser refreshes by forwarding SPA routes to `/index.html`.

**Run Command**:
```bash
cd "Module Project/Module - 4/employee-management-react/employee-management-react-springboot/employee-management-react-frontend"
npm install
npm run dev
```

---

### Module - 5: Spring Data JPA & Thymeleaf Web Mini-Project

#### 🏥 Main Project: `hospital-appointment-system`
**Hospital Appointment System — Mini Project With HTML/CSS/Thymeleaf**
- **Technologies**: Spring Boot 3.3.4, Spring Web, Spring Data JPA (Hibernate), MySQL Connector / Embedded H2 Database, Thymeleaf.
- **Relational Architecture**:
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

**Run Command**:
```bash
cd "Module Project/Module - 5/hospital-appointment-system"
.\mvnw.cmd spring-boot:run
```
*Access via browser: `http://localhost:8080`*

---

## Assignment Documentation

### Assignment - 1: Spring Boot Task - 5-Page Website
A multi-page responsive web application built using **Spring Boot** and **Spring Web** with clean static page routing and modern styling.

**Run Command**:
```bash
cd "Assignment Project/Assignment - 1/spring-boot-web-project"
.\mvnw spring-boot:run
```
