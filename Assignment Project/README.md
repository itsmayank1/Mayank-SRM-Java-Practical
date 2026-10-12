# Assignment Project - SRM Full Stack Practical

This directory contains classwork and practical assignments for the Java Full Stack curriculum at SRM Institute of Science and Technology.

## Structure

```
Assignment Project/
└── Assignment - 1/
    └── spring-boot-web-project/     (Spring Boot 5-Page Static Website)
```

---

## Assignment - 1: Spring Boot Task - 5-Page Website

A clean, responsive 5-page web application developed using **Spring Boot** and **Spring Web** with purely static HTML and CSS architecture (without requiring Thymeleaf, JPA, or a database).

### Technologies Used:
- **Java** (JDK 17+)
- **Spring Boot 3.x / 4.x**
- **Spring Web** (`spring-boot-starter-web`)
- **HTML5 & CSS3** (Vanilla CSS with modular layout)
- **Embedded Apache Tomcat**

### Project Pages & Routing:
1. **Home Page (`/` or `/index.html`)**:
   - Welcome banner, project introduction, and global navigation menu.
2. **Project Creator Page (`/about.html`)**:
   - Details of the creator (Name, College, Course, Year, Skills, Introduction).
3. **Product Page (`/product.html`)**:
   - Comprehensive overview of the Student Management System (Description, Purpose, Tech stack, Target users).
4. **Features Page (`/features.html`)**:
   - Project feature checklist (Add, View, Update, Delete, Search, Student Reports) and an interactive live student demo table.
5. **Contact Page (`/contact.html`)**:
   - Contact details and interactive feedback message form with response alerts.

### Project Architecture:
```
spring-boot-web-project/
├── src/
│   ├── main/
│   │   ├── java/com/example/springbootweb/
│   │   │   └── SpringbootwebApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── css/
│   │           │   └── style.css
│   │           ├── index.html
│   │           ├── about.html
│   │           ├── product.html
│   │           ├── features.html
│   │           └── contact.html
│   └── test/
└── pom.xml
```

### How to Run:

1. Navigate to the project directory:
   ```bash
   cd "Assignment Project/Assignment - 1/spring-boot-web-project"
   ```

2. Run with the Maven wrapper:
   ```bash
   # Windows PowerShell / CMD
   .\mvnw spring-boot:run

   # Linux / macOS
   ./mvnw spring-boot:run
   ```

3. Open your browser and navigate to:
   - **Home**: [http://localhost:8080/](http://localhost:8080/)
   - **About**: [http://localhost:8080/about.html](http://localhost:8080/about.html)
   - **Product**: [http://localhost:8080/product.html](http://localhost:8080/product.html)
   - **Features**: [http://localhost:8080/features.html](http://localhost:8080/features.html)
   - **Contact**: [http://localhost:8080/contact.html](http://localhost:8080/contact.html)
