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
│   └── Module - 3/
│       ├── employee-management-web/         (MAIN PROJECT: Employee Management Web App)
│       └── Additional Projects/             (Supplementary Spring Core & MVC Projects)
│           ├── dependency-injection-ioc/    (Spring Core XML-based IoC & DI)
│           ├── first-spring-app/            (Spring Core Annotation Context)
│           ├── request-mapping-controller/  (Spring Boot REST Web Controller)
│           └── spring-mvc/                  (Spring Web MVC WAR Application)
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
- **Clean Number Formatting**: Integer results display cleanly without redundant decimals, with zero-division protection.

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
- **Security & JWT**: Custom login page (`/login`), role-based authorization (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_USER`), and REST JWT token generator (`/api/auth/token`).
- **Web UI**: Interactive dashboard with payroll & department metrics, search/filter, and full CRUD.

**Run Command**:
```bash
cd "Module Project/Module - 3/employee-management-web"
mvn spring-boot:run
```
*Access via browser: `http://localhost:8080` (Default credentials: `admin` / `admin123`).*

---

#### 📚 Additional Projects (`Additional Projects/`)

1. **`dependency-injection-ioc`**: Classic Spring XML configuration (`applicationContext.xml`), constructor/setter injection, bean lifecycle callbacks, and multiple messaging services.
   ```bash
   cd "Module Project/Module - 3/Additional Projects/dependency-injection-ioc"
   mvn compile exec:java
   ```

2. **`first-spring-app`**: Annotation-driven Spring context using `@Configuration`, `@ComponentScan`, `@Service`, and `@Autowired`.
   ```bash
   cd "Module Project/Module - 3/Additional Projects/first-spring-app"
   mvn compile exec:java
   ```

3. **`request-mapping-controller`**: Spring Boot RESTful Web API implementing `@RestController` and `@RequestMapping("/api/students")` with full CRUD operations.
   ```bash
   cd "Module Project/Module - 3/Additional Projects/request-mapping-controller"
   mvn spring-boot:run
   ```

4. **`spring-mvc`**: Traditional Spring Web MVC architecture with `DispatcherServlet`, `CalculatorWebController`, and interactive JSP views packaged into a WAR.
   ```bash
   cd "Module Project/Module - 3/Additional Projects/spring-mvc"
   mvn clean package -DskipTests
   ```

---

## Assignment Documentation

### Assignment - 1: Spring Boot Task - 5-Page Website
A multi-page responsive web application built using **Spring Boot** and **Spring Web** with clean static page routing and modern styling.

- **Pages**:
  - `Home Page` (`http://localhost:8080/`): Welcome header, introduction, and global navigation menu.
  - `Project Creator Page` (`http://localhost:8080/about.html`): Profile of the student developer.
  - `Product Page` (`http://localhost:8080/product.html`): Application info, target audience, and tech stack.
  - `Features Page` (`http://localhost:8080/features.html`): Checklist of capabilities and interactive demo table.
  - `Contact Page` (`http://localhost:8080/contact.html`): Student contact details and feedback form.

**Run Command**:
```bash
cd "Assignment Project/Assignment - 1/spring-boot-web-project"
.\mvnw spring-boot:run
```
