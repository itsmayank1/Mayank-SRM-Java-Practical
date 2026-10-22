# Module - 3: Spring Framework & Spring Boot

**Student**: Mayank Upadhyay  
**Course**: SRM-IST CSE Core Java Full Stack Practical  

---

## 📂 Module Structure

This module contains the primary enterprise web mini-project alongside four supplementary projects covering foundational Spring Core, Spring MVC, and REST controllers.

```
Module - 3/
├── employee-management-web/               <-- MAIN MODULE - 3 PROJECT
│   ├── src/main/java/com/example/
│   │   ├── security/ (SecurityConfig.java, JwtService.java)
│   │   ├── controller/ (Login, Dashboard, Employee, Department)
│   │   ├── model/ (Employee, Department, Role)
│   │   └── service/ (EmployeeService, DepartmentService)
│   ├── src/main/resources/
│   │   ├── static/ (style.css)
│   │   └── templates/ (login, dashboard, employees, employee-form, department)
│   └── pom.xml
└── Additional Projects/                   <-- SUPPLEMENTARY PRACTICAL PROJECTS
    ├── dependency-injection-ioc/          (XML-based IoC Container & Bean Lifecycle)
    ├── first-spring-app/                  (Annotation-driven Spring Core)
    ├── request-mapping-controller/        (Spring Boot REST Web API)
    └── spring-mvc/                        (Spring Web MVC with JSP views)
```

---

## 🌟 1. Main Project: `employee-management-web`

**Employee Management System — Mini Project With Web Page**
- **Architecture**: Spring Boot 3 + Spring MVC + Spring Security 6 + JJWT 0.12.6 + Thymeleaf.
- **Storage**: In-memory `ArrayList` (`CopyOnWriteArrayList`) with synchronized department relationships.
- **Security**: Custom form login (`/login`), role-based access control (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_USER`), and JWT token generation endpoint (`/api/auth/token`).
- **Web UI**: Interactive Thymeleaf dashboard with live metrics, full Employee CRUD with filters, and Department management.
- **Testing**: 14 automated unit and integration tests passing with 100% success rate.

**Quick Run**:
```powershell
cd employee-management-web
mvn spring-boot:run
```
*Access via browser at `http://localhost:8080` (Default admin credentials: `admin` / `admin123`).*

---

## 📚 2. Additional Projects (`Additional Projects/`)

### A. `dependency-injection-ioc`
- Demonstrates Spring Core XML ApplicationContext (`applicationContext.xml`).
- Setter injection, Constructor injection, and multiple notification service implementations (`EmailMessageService`, `SMSMessageService`, `WhatsAppMessageService`).
- Singleton vs. Prototype bean scopes and lifecycle callbacks (`init-method`, `destroy-method`).

### B. `first-spring-app`
- Pure Java annotation-based Spring configuration (`@Configuration`, `@ComponentScan`).
- Dependency injection using `@Autowired` across layered services with automated test coverage.

### C. `request-mapping-controller`
- Spring Boot RESTful Web API implementing `@RestController` and `@RequestMapping("/api/students")`.
- Demonstrates full CRUD operations (`GET`, `POST`, `PUT`, `DELETE`) with query filtering.

### D. `spring-mvc`
- Traditional Spring Web MVC architecture using `DispatcherServlet`, XML web context (`spring-servlet.xml`, `web.xml`), `CalculatorWebController`, and dynamic JSP views.
