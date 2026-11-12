# Module - 4: Employee Management System (React 19 SPA + Spring Boot REST)

**Student**: Mayank Upadhyay  
**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## 📂 Module Architecture

```
Module - 4/
└── employee-management-react/
    ├── README.md
    ├── employee-management-react-backend/       (Self-Contained Spring Boot 3.3.4 REST Backend)
    │   ├── pom.xml
    │   ├── mvnw / mvnw.cmd
    │   └── src/
    │       ├── main/java/com/example/employee/
    │       │   ├── EmployeeManagementReactApplication.java
    │       │   ├── config/SecurityConfig.java
    │       │   ├── controller/ (EmployeeController, DepartmentController, LoginController, DashboardController, ReactForwardController)
    │       │   ├── model/ (Employee, Department, Role)
    │       │   ├── security/JwtService.java
    │       │   └── service/ (EmployeeService, DepartmentService)
    │       ├── main/resources/
    │       │   ├── application.properties (port 8080)
    │       │   └── static/ (Production-built React 19 SPA bundle)
    │       └── test/java/com/example/employee/ (MockMvc Automated Tests)
    │
    └── employee-management-react-springboot/
        ├── Spring Boot integration files.docx
        ├── employee-management-react-springboot.zip
        └── employee-management-react-frontend/   (Customized React 19 + Vite SPA Source)
            ├── package.json
            ├── vite.config.js
            ├── index.html
            ├── README.md
            ├── src/
            │   ├── App.jsx
            │   ├── main.jsx
            │   ├── styles.css (Modern responsive Emerald & Slate Theme)
            │   ├── components/ (Layout.jsx, StatCard.jsx)
            │   ├── context/ (AppContext.jsx with Toast & Activity Audit Feed)
            │   ├── pages/ (Login.jsx, Dashboard.jsx, Employees.jsx, EmployeeForm.jsx, Departments.jsx)
            │   └── services/ (api.js with Network Fallback & CSV Export)
            └── backend-integration/ (Instructor reference replacement controllers)
```

---

## 🌟 Enhanced Features & Customizations

Compared to the minimal reference template provided in class, this Module 4 implementation has been substantially elevated with enterprise-grade features:

### 1. ⚛️ Modern React 19 + Vite Architecture
- **Single Page Application (SPA)**: Zero page reloads; client-side routing via React Router v7 (`react-router-dom`).
- **Resilient API Layer (`api.js`)**: Seamless proxy to Spring Boot backend (`/api/**`) with automatic offline simulated state fallback, ensuring evaluator can run either Vite dev or the unified backend.
- **Direct CSV Data Export**: "📥 Export CSV" button generating Excel-compatible `employees_export_*.csv` on the fly.

### 2. 📊 Executive Dashboard & Workforce Analytics (`Dashboard.jsx`)
- **4 Real-Time KPI Cards**: Total Employees, Active Departments, Monthly Payroll (₹), and Average Salary (₹).
- **Workforce Distribution Visualizer**: Dynamic percentage progress bars tracking staff allocation across each department.
- **Salary Tier Classification**: Real-time breakdown into Entry (`< ₹60k`), Mid (`₹60k - ₹80k`), and Senior (`> ₹80k`) compensation tiers.
- **Live Activity Audit Feed**: Tracks recent logins, creations, updates, and deletions with timestamps and category dots.
- **Quick Action Launchpad**: Instant shortcuts for registering employees, adding departments, and exporting data.

### 3. 👥 Advanced Employee Directory (`Employees.jsx`)
- **Multi-Factor Search & Filtering**: Instant live search across Name, Email, and Department; filter by Department dropdown; filter by Salary Tier.
- **Multi-Column Sorting**: Interactive sort headers with visual indicator arrows (`▲` / `▼`) for Employee Name, Department, and Salary.
- **Interactive Quick-View Modal**: Clicking any employee row opens a sleek slide-out profile modal with ID, corporate email, salary, annual CTC, and employment status.
- **Initials Avatars**: Dynamic avatar chips styled with emerald glow accents.
- **Role-Based Permission Guards**:
  - `HR`: Add new employees, edit all records, delete records with confirmation modal.
  - `Manager`: View directory, edit employee details.
  - `Employee`: Read-only access with clear permissions note.

### 4. 🏢 Department Operations Hub (`Departments.jsx`)
- **Rich Team Cards**: Department code (`DEPT-01`), department name, team headcount, total department payroll, and average salary.
- **Expandable Member Rosters**: "👥 View Members" drawer inside each card listing the individual employees assigned to that department.
- **Inline Department Creation**: Fast creation form for HR with duplicate department detection and feedback.

### 5. ⚡ Frictionless One-Click Demo Sign-In (`Login.jsx`)
- Interactive one-click demo login buttons for:
  - **HR** (`hr` / `1234`): Full Admin & CRUD privileges.
  - **Manager** (`manager` / `1234`): View & edit permissions.
  - **Employee** (`employee` / `1234`): Standard directory viewing.
- Student & course attribution display.

---

## 🛠️ How to Run & Verify

### Mode 1: Unified Spring Boot Application (Serving React SPA)
*Runs both the Spring Boot REST backend and the compiled React 19 SPA on a single port (`8080`):*
```powershell
cd "Module Project\Module - 4\employee-management-react\employee-management-react-backend"
.\mvnw.cmd spring-boot:run
```
*Open your browser at:* **`http://localhost:8080`**

### Mode 2: React Vite Development Server (Hot Reload)
*Runs Vite dev server with proxy to backend (`http://localhost:5173`):*
```powershell
cd "Module Project\Module - 4\employee-management-react\employee-management-react-springboot\employee-management-react-frontend"
npm install
npm run dev
```
*Open your browser at:* **`http://localhost:5173`**

### Mode 3: Automated Test Execution
```powershell
cd "Module Project\Module - 4\employee-management-react\employee-management-react-backend"
.\mvnw.cmd test
```
*Executes MockMvc integration tests verifying authentication, session tokens, and route forwarding.*
