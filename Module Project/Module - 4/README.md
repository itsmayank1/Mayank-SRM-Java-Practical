# Module - 4: Employee Management System (React Frontend + Spring Boot REST)

**Student**: Mayank Upadhyay  
**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## 📂 Module Structure

```
Module - 4/
└── employee-management-react/
    ├── employee-management-react-springboot.zip
    ├── Spring Boot integration files.docx
    └── employee-management-react-springboot/
        └── employee-management-react-frontend/
            ├── package.json
            ├── vite.config.js
            ├── index.html
            ├── README.md
            ├── src/
            │   ├── App.jsx
            │   ├── main.jsx
            │   ├── styles.css
            │   ├── components/ (Layout.jsx, StatCard.jsx)
            │   ├── context/ (AppContext.jsx)
            │   ├── pages/ (Login.jsx, Dashboard.jsx, Employees.jsx, EmployeeForm.jsx, Departments.jsx)
            │   └── services/ (api.js, README.md)
            └── backend-integration/
                ├── README.md
                ├── StaticReactForwardController.java.txt
                ├── config/
                │   └── SecurityConfig.java
                └── controller/
                    ├── LoginController.java
                    ├── EmployeeController.java
                    ├── DepartmentController.java
                    ├── DashboardController.java
                    └── ReactForwardController.java
```

---

## ⚛️ Module Overview

**Module - 4** builds an interactive Single Page Application (SPA) frontend using **React 19** and **Vite**, connecting to the Spring Boot REST backend created in Module 3.

### Key Capabilities:
- **Client-Side SPA Architecture**: React Router v7 (`react-router-dom`) with client-side routing, protected layouts, and dynamic route redirection.
- **Role-Based UI & Access Control**:
  - `HR`: View all records, register/update/delete employees, and add departments.
  - `Manager`: View and edit employees.
  - `Employee`: Read-only access to employee records and departments.
  - Demo accounts: `hr / 1234`, `manager / 1234`, `employee / 1234`.
- **Global State Management**: React Context (`AppContext.jsx`) managing session tokens, user profile, employee directory, department list, and notifications.
- **RESTful API Service Layer (`api.js`)**:
  - `POST /api/auth/login`, `GET /api/auth/me`, `POST /api/auth/logout`
  - `GET /api/employees`, `GET /api/employees/{id}`, `POST /api/employees`, `PUT /api/employees/{id}`, `DELETE /api/employees/{id}`
  - `GET /api/departments`, `POST /api/departments`
- **Backend REST Controllers (`backend-integration/`)**:
  - Drops directly into the Spring Boot backend (`com.example.employee`) to replace Thymeleaf views with pure JSON REST endpoints while preserving all existing `EmployeeService`, `DepartmentService`, `Role`, and `JwtService` logic.
  - `ReactForwardController.java` routes all direct browser navigation and page reloads cleanly to `index.html`.

---

## 🚀 Execution Guide

### Option 1: Development Server (Vite + Spring Boot Backend)
1. Ensure the Spring Boot backend is running on `http://localhost:8080`.
2. Start the Vite React development server:
   ```powershell
   cd "Module Project\Module - 4\employee-management-react\employee-management-react-springboot\employee-management-react-frontend"
   npm install
   npm run dev
   ```
3. Open `http://localhost:5173`. Requests to `/api/*` are automatically proxied to Spring Boot.

### Option 2: Integrated Production Build (Spring Boot Serving React)
1. Build the production React assets:
   ```powershell
   cd "Module Project\Module - 4\employee-management-react\employee-management-react-springboot\employee-management-react-frontend"
   npm install
   npm run build
   ```
2. Copy the generated files inside `dist/` into the Spring Boot project's static folder:
   `src/main/resources/static/`
3. Start the Spring Boot application:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```
4. Access the full application directly at **`http://localhost:8080`**.
