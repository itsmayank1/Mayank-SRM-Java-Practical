# Employee Management System — Mini Project With Web Page

**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Module**: Module - 3 (Main Enterprise Web Application)  
**Author**: Mayank Upadhyay  
**Technology Stack**: Spring Boot 3.3.4, Spring MVC, Spring Security 6, Spring JWT (JJWT 0.12.6), Thymeleaf, In-Memory ArrayList Storage

---

## 📌 Project Overview & Comparison with Teacher Baseline

This project was built following the official SRM-IST Module 3 curriculum, referencing the instructor's architecture while significantly expanding and elevating the design and capabilities to deliver an enterprise-grade experience.

### 🌟 Key Enhancements Over Instructor's Reference Version:
1. **Elevated & Distinct Design System**:
   - Rather than the basic flat green styling in the reference code, this project features a modern **Deep Slate & Royal Indigo** SaaS design (`#0f172a`, `#2563eb`), metric cards with glowing status indicators, interactive sort buttons, and mobile-responsive layouts.
2. **Advanced Multi-Parameter Search & Filtering**:
   - Retains the instructor's page-based sorting and pagination, while adding a live keyword search bar (filtering by name, email, title, or department) and a department dropdown filter.
3. **Dual Authentication & Role Authorization**:
   - Supports the instructor's demo credentials (`hr / 1234`, `manager / 1234`, `employee / 1234`) and standard administrative credentials (`admin / admin123`).
   - Session-based authentication handles web page view rights, while the `/api/auth/token` endpoint produces signed HMAC-SHA256 JWT tokens for REST API testing.
4. **Executive Dashboard Analytics**:
   - Live KPI cards calculating total headcount, active vs. on-leave staff, department count, total payroll, and average salary.
   - Department breakdown widget showing team size distribution and recent hires.
5. **CSV Workforce Export**:
   - Direct `Export CSV` action on the employee directory (`/employees/export`) allowing one-click download of the active roster.
6. **100% Test Coverage**:
   - 15 automated unit and integration tests verifying controllers, ArrayList CRUD operations, sorting/pagination logic, and JWT token lifecycle.

---

## 📂 Architecture & Directory Structure

```
employee-management-web/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── EmployeeManagementWebApplication.java
    │   │   ├── security/
    │   │   │   ├── SecurityConfig.java
    │   │   │   └── JwtService.java
    │   │   ├── controller/
    │   │   │   ├── LoginController.java
    │   │   │   ├── DashboardController.java
    │   │   │   ├── EmployeeController.java
    │   │   │   └── DepartmentController.java
    │   │   ├── model/
    │   │   │   ├── Employee.java
    │   │   │   ├── Department.java
    │   │   │   └── Role.java (enum: HR, MANAGER, EMPLOYEE)
    │   │   └── service/
    │   │       ├── EmployeeService.java
    │   │       └── DepartmentService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       │   ├── css/style.css
    │       │   └── style.css
    │       └── templates/
    │           ├── login.html
    │           ├── dashboard.html
    │           ├── employees.html
    │           ├── employee-form.html
    │           └── departments.html
    └── test/java/com/example/
        ├── EmployeeManagementWebApplicationTests.java
        ├── EmployeeServiceTest.java
        ├── EmployeeControllerTest.java
        └── JwtServiceTest.java
```

---

## 🔑 Demo User Accounts

| Username | Password | Role | Permissions |
|---|---|---|---|
| `hr` | `1234` | `HR` | Full administrative control (Add, Edit, Delete employees & departments) |
| `manager` | `1234` | `MANAGER` | Edit employee records, view departments and dashboard |
| `employee` | `1234` | `EMPLOYEE` | Read-only access to employee directory and dashboard metrics |
| `admin` | `admin123` | `HR` | Full administrative access |

---

## 🚀 Build and Run Instructions

### 1. Run Automated Test Suite
```powershell
mvn clean test
```
*Result: 15 tests run, 0 failures, 0 errors.*

### 2. Launch Local Development Server
```powershell
mvn spring-boot:run
```
Open your browser at: **`http://localhost:8080`**

### 3. Run Standalone Executable JAR
```powershell
mvn clean package -DskipTests
java -jar target/employee-management-web-1.0-SNAPSHOT.jar
```

---

## 🌐 JWT REST API Demonstration

### Generate Signed JWT Token
```bash
curl -X POST http://localhost:8080/api/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username": "hr", "password": "1234"}'
```

### Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "hr",
  "role": "HR"
}
```

### Download Employee Roster as CSV:
```bash
curl -O http://localhost:8080/employees/export
```
