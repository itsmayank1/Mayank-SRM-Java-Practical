# Spring Core Projects - SRM Java Full Stack Practical

This section contains Spring Framework projects demonstrating Inversion of Control (IoC), Dependency Injection (DI), Annotation Configuration, and Spring Web MVC.

## Projects Overview

### 1. `dependency-injection-ioc` (XML-based IoC Container)
Demonstrates classic Spring XML configuration, decoupled interfaces, constructor injection, setter injection, bean lifecycle callbacks, and bean scopes.

#### Key Features & Advancements:
- **Service Abstraction**: `MessageService` interface implemented by `EmailMessageService`, `SMSMessageService`, and `WhatsAppMessageService`.
- **Dual Injection Support**:
  - **Setter Injection**: Configured via `<property>` tag for `messagePrinterSetter`.
  - **Constructor Injection**: Configured via `<constructor-arg>` tag for `messagePrinterConstructor`.
- **Bean Lifecycle Callbacks**: `init-method="init"` and `destroy-method="cleanup"` declared in `applicationContext.xml`.
- **Bean Scopes**: Side-by-side comparison of **Singleton** (shared instance) vs **Prototype** (new instance on every `getBean` lookup).

#### Build & Run:
```bash
cd "Spring Core/dependency-injection-ioc"
mvn compile exec:java
```

---

### 2. `first-spring-app` (Annotation-based Spring Context)
Demonstrates modern Spring annotation configuration without XML files.

#### Key Features & Advancements:
- **Stereotype Annotations**: `@Configuration`, `@ComponentScan`, `@Service`, and `@Component`.
- **Autowired Dependency Injection**: Automatic constructor autowiring in `OrderService` injecting `PaymentGateway` and `MyService`.
- **Runtime Execution**: Dynamic container bootstrapping via `AnnotationConfigApplicationContext`.

#### Build & Run:
```bash
cd "Spring Core/first-spring-app"
mvn compile exec:java
```

---

### 3. `spring-mvc` (Spring Web MVC Application)
Demonstrates enterprise Model-View-Controller (MVC) web application architecture packaged as a deployable WAR.

#### Key Features & Advancements:
- **DispatcherServlet Architecture**: Front-controller pattern registered in `web.xml`.
- **View Resolver**: `InternalResourceViewResolver` resolving JSP views under `/WEB-INF/views/`.
- **Web Controller**: `CalculatorWebController` handling `@GetMapping` for the interface and `@PostMapping` with `@RequestParam` and `Model` data binding for live mathematical evaluation.
- **Interactive UI**: Clean, responsive calculator web interface with error banner and equation result cards.

#### Build:
```bash
cd "Spring Core/spring-mvc"
mvn clean package
```
*(Produces `target/spring-mvc.war` for deployment on any standard servlet container such as Apache Tomcat or Jetty)*
