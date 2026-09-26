# Module - 3: Spring Framework & Spring Boot

This module covers the progression from Spring Core IoC / Dependency Injection to Spring Web MVC and Spring Boot REST Controllers.

## Projects in this Module

```
Module - 3/
├── dependency-injection-ioc/        (XML-based Spring IoC Container)
├── first-spring-app/                (Annotation-driven Spring Context)
├── spring-mvc/                      (Spring Web MVC WAR Application)
└── request-mapping-controller/      (Spring Boot REST Web API Application)
```

---

### 1. `dependency-injection-ioc` (XML-based IoC & DI)
Demonstrates classic Spring XML configuration (`applicationContext.xml`), loose coupling via interfaces, constructor injection, setter injection, bean lifecycle callbacks, and bean scopes.

- **Services**: `MessageService` with `EmailMessageService`, `SMSMessageService`, and `WhatsAppMessageService`.
- **Injection Styles**: Both Setter and Constructor injection demonstrated side-by-side.
- **Scopes**: Singleton (shared instance) vs Prototype (unique instance on every `getBean`).
- **Lifecycle**: `init-method="init"` and `destroy-method="cleanup"` callbacks.

**How to Run**:
```bash
cd "Module Project/Module - 3/dependency-injection-ioc"
mvn compile exec:java
```

---

### 2. `first-spring-app` (Annotation-Driven Spring Context)
Demonstrates modern Spring annotation configuration without XML files using `@Configuration`, `@ComponentScan`, `@Service`, `@Component`, and `@Autowired`.

- **Layered Architecture**: Demonstrates constructor autowiring across `PaymentGateway`, `OrderService`, and `MyService`.
- **Container**: Bootstrapped via `AnnotationConfigApplicationContext`.

**How to Run**:
```bash
cd "Module Project/Module - 3/first-spring-app"
mvn compile exec:java
```

---

### 3. `spring-mvc` (Spring Web MVC Webapp)
Demonstrates standard Spring Web MVC architecture packaged as a deployable WAR.

- **Front Controller**: `DispatcherServlet` registered in `web.xml`.
- **View Resolver**: `InternalResourceViewResolver` resolving JSP views under `/WEB-INF/views/`.
- **Controller**: `CalculatorWebController` handling `@GetMapping("/calculator")` and `@PostMapping("/calculate")`.
- **Packaging**: Packaged as deployable `target/spring-mvc.war`.

**How to Build**:
```bash
cd "Module Project/Module - 3/spring-mvc"
mvn clean package -DskipTests
```

---

### 4. `request-mapping-controller` (Spring Boot Web Controller)
A modern Spring Boot application created with `start.spring.io` using the `Spring Web` dependency as demonstrated in class.

- **Annotations**: `@SpringBootApplication`, `@RestController`, `@RequestMapping("/api/students")`.
- **Endpoints**:
  - `GET /api/students`: Retrieve list of all students.
  - `GET /api/students/{id}`: Retrieve single student by ID (`@PathVariable`).
  - `GET /api/students/search`: Filter students by department or name query parameter (`@RequestParam`).
  - `POST /api/students`: Create new student entity (`@RequestBody`).
  - `PUT /api/students/{id}`: Update student record.
  - `DELETE /api/students/{id}`: Remove student record.

**How to Run**:
```bash
cd "Module Project/Module - 3/request-mapping-controller"
mvn spring-boot:run
```
