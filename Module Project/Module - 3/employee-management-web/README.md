# Employee Management System — Mini Project With Web Page

**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Module**: Module - 3 (Main Project)  
**Author**: Mayank Upadhyay  
**Technology Stack**: Spring Boot 3.3.4, Spring MVC, Spring Security 6, Spring JWT (JJWT 0.12.6), Thymeleaf, ArrayList Storage

---

## 📌 Project Overview

The **Employee Management System (EMS)** is an enterprise web application developed as the primary capstone project for **Module - 3**. Built using modern **Spring Boot 3** and **Spring MVC**, it features responsive **Thymeleaf** templates, robust role-based authentication via **Spring Security**, token-based authorization via **JJWT**, and in-memory persistence using synchronized **`ArrayList`** collections for both Employees and Departments.

```
employee-management-web/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/example/
    │   │       ├── EmployeeManagementWebApplication.java
    │   │       ├── security/
    │   │       │   ├── SecurityConfig.java
    │   │       │   └── JwtService.java
    │   │       ├── controller/
    │   │       │   ├── LoginController.java
    │   │       │   ├── DashboardController.java
    │   │       │   ├── EmployeeController.java
    │   │       │   └── DepartmentController.java
    │   │       ├── model/
    │   │       │   ├── Employee.java
    │   │       │   ├── Department.java
    │   │       │   └── Role.java
    │   │       └── service/
    │   │           ├── EmployeeService.java
    │   │           └── DepartmentService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       │   └── style.css
    │       └── templates/
    │           ├── login.html
    │           ├── dashboard.html
    │           ├── employees.html
    │           ├── employee-form.html
    │           └── department.html
    └── test/
        └── java/com/example/
            ├── EmployeeManagementWebApplicationTests.java
            ├── EmployeeServiceTest.java
            ├── EmployeeControllerTest.java
            └── JwtServiceTest.java
```

---

## 🚀 Key Features

1. **Role-Based Security (`Spring Security 6`)**:
   - Secure custom login interface (`/login`).
   - Role authorization:
     - `ROLE_ADMIN`: Full access (create, edit, delete employees and departments).
     - `ROLE_MANAGER`: Create and edit employees; view departments and dashboard.
     - `ROLE_USER`: View employee directory and dashboard analytics.

2. **JSON Web Token (`JJWT 0.12.6`) Integration**:
   - Programmatic JWT token generation via `JwtService`.
   - Dedicated REST authentication endpoint (`/api/auth/token`) to generate signed HMAC-SHA256 bearer tokens.
   - Methods to extract claims, roles, expiration, and validate tokens for client applications.

3. **In-Memory ArrayList Persistence (`ArrayList`)**:
   - Department and Employee records stored in synchronized, thread-safe `ArrayList` structures (`CopyOnWriteArrayList`).
   - Automated ID generation (`AtomicLong`).
   - Dynamic synchronization between employee assignments and department headcount metrics.

4. **Full Employee & Department CRUD**:
   - Create, Read, Update, and Delete employees with validation.
   - Filter employees by search keyword (name, email, designation) and department dropdown.
   - Add new departments with code, office location, and head of department.

5. **Responsive Dashboard**:
   - Live metrics: Total Employees, Active Staff, Department Count, and Monthly Payroll.
   - Department breakdown with team size indicators.
   - Recent team member roster.

---

## 🔑 Default Credentials

| Username | Password | Role | Permissions |
|---|---|---|---|
| `admin` | `admin123` | `ROLE_ADMIN` | Full access (Add, Edit, Delete, View) |
| `manager` | `manager123` | `ROLE_MANAGER` | Add & Edit Employees, View All |
| `employee` | `employee123` | `ROLE_USER` | View Directory & Dashboard Only |

---

## 🛠️ Build and Execution Instructions

### Prerequisites
- Java JDK 17, 21, or 22
- Apache Maven 3.8+

### 1. Run Automated Test Suite
```powershell
mvn clean test
```
*Executes all 14 unit and integration tests including Spring Security filter chains, MockMvc endpoints, service CRUD, and JWT tests.*

### 2. Run Application Locally
```powershell
mvn spring-boot:run
```
Once initialized, navigate to: **`http://localhost:8080`**

### 3. Run Executable JAR
```powershell
mvn clean package -DskipTests
java -jar target/employee-management-web-1.0-SNAPSHOT.jar
```

---

## 🌐 API & JWT Demonstration

### Generate JWT Token
```bash
curl -X POST http://localhost:8080/api/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```
**Sample Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "role": "ROLE_ADMIN"
}
```

### Fetch Employees via REST API
```bash
curl http://localhost:8080/api/employees
```

### Filter Employees by Department
```bash
curl "http://localhost:8080/api/employees?deptId=1"
```
