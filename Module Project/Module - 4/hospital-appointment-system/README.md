# Hospital Appointment System — Mini Project

**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Module**: Module Project - 4 (Mini Project)  
**Author**: Mayank Upadhyay  
**Technology Stack**: Spring Boot 3.3.4, Spring Web, Spring Data JPA, Hibernate, Thymeleaf, MySQL Connector / H2 Embedded Fallback, Responsive Modern Healthcare CSS

---

## 📌 Project Overview

The **Hospital Appointment System** is an enterprise-grade clinical management web application developed for **Module Project - 4**. Built following the blackboard specifications, it manages specialized medical departments, physician rosters, patient registrations, and outpatient appointment bookings with complete relational data persistence via **Spring Data JPA**.

```
hospital-appointment-system/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/
    │   │   ├── HospitalAppointmentSystemApplication.java
    │   │   ├── controller/
    │   │   │   └── HospitalWebController.java
    │   │   ├── entity/
    │   │   │   ├── Department.java
    │   │   │   ├── Doctor.java
    │   │   │   ├── Patient.java
    │   │   │   └── Appointment.java
    │   │   └── repository/
    │   │       ├── DepartmentRepository.java
    │   │       ├── DoctorRepository.java
    │   │       ├── PatientRepository.java
    │   │       └── AppointmentRepository.java
    │   └── resources/
    │       ├── application.properties
    │       ├── static/
    │       │   ├── style.css
    │       │   └── css/style.css
    │       └── templates/
    │           ├── index.html
    │           ├── departments.html
    │           ├── doctors.html
    │           ├── appointments.html
    │           └── patients.html
    └── test/java/com/example/
        ├── HospitalAppointmentSystemApplicationTests.java
        ├── HospitalRepositoryTest.java
        └── HospitalWebControllerTest.java
```

---

## 🏥 Key Entities & Relational Architecture

1. **`Department` (`departments` table)**:
   - Primary key: `id` (Auto-increment)
   - Attributes: `name` (unique), `description`, `location`, `headDoctor`
   - Relationship: One-to-Many (`@OneToMany`) with `Doctor`

2. **`Doctor` (`doctors` table)**:
   - Primary key: `id` (Auto-increment)
   - Attributes: `name`, `email`, `phone`, `specialization`, `qualification`, `availableDays`, `consultingHours`
   - Relationships: Many-to-One (`@ManyToOne`) with `Department`, One-to-Many with `Appointment`

3. **`Patient` (`patients` table)**:
   - Primary key: `id` (Auto-increment)
   - Attributes: `name`, `email`, `phone`, `age`, `gender`, `bloodGroup`, `address`
   - Relationship: One-to-Many with `Appointment`

4. **`Appointment` (`appointments` table)**:
   - Primary key: `id` (Auto-increment)
   - Foreign keys: `doctor_id`, `patient_id`
   - Attributes: `appointmentDate`, `appointmentTime`, `reason`, `status` (`Scheduled`, `Confirmed`, `Completed`, `Cancelled`), `roomNumber`, `consultationFee`

---

## 🚀 Key Features

- **Executive Clinical Dashboard (`/` & `/index`)**: Real-time KPI statistics (Total Departments, Active Doctors, Registered Patients, Total Appointments), quick appointment booking widget, and today's consultation schedule.
- **Departments Roster (`/departments`)**: Full directory of hospital wings, consultation rooms, department heads, and doctor team counts.
- **Specialist Physician Directory (`/doctors`)**: Search by name or specialty, filter by clinical department, review consulting days/hours, and register new medical officers.
- **Patient Records (`/patients`)**: Search by name, phone, or email; register patients with age, gender, blood group, and contact details.
- **Appointment Desk (`/appointments`)**: Schedule outpatient consultations, filter by status or doctor or date, quick status toggles (`✔ Confirm`, `✓✓ Complete`, `✕ Cancel`), and room/fee tracking.
- **RESTful Endpoints (`/api/...`)**: JSON endpoints for automated testing and client integration (`/api/stats`, `/api/departments`, `/api/doctors`, `/api/patients`, `/api/appointments`).
- **Database Resilience**: Configured with both `mysql-connector-j` and embedded `H2` fallback so the system runs smoothly out of the box in zero-config mode, with complete instructions to switch to production MySQL.

---

## 🛠️ Build and Execution Instructions

### Prerequisites
- Java JDK 17, 21, or 22
- Apache Maven or included `./mvnw.cmd`

### 1. Run Automated Test Suite
```powershell
cd "Module Project\Module - 4\hospital-appointment-system"
.\mvnw.cmd clean test
```
*Runs 12 unit and integration tests verifying JPA entities, database relationships, and web controller mappings.*

### 2. Run Application Locally
```powershell
cd "Module Project\Module - 4\hospital-appointment-system"
.\mvnw.cmd spring-boot:run
```
Once initialized, open your browser at: **`http://localhost:8080`**

### 3. Run Standalone Executable JAR
```powershell
cd "Module Project\Module - 4\hospital-appointment-system"
java -jar target\hospital-appointment-system-1.0-SNAPSHOT.jar
```

---

## 🗄️ Database Configuration (H2 & MySQL)

In `src/main/resources/application.properties`:
- **Default (Embedded Zero-Config)**: Uses H2 In-Memory DB (`jdbc:h2:mem:hospital_db`). Pre-seeds sample cardiology, neurology, orthopedics, pediatrics, and medicine records on startup.
- **H2 Console**: Accessible at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:hospital_db`, User: `sa`, Password: *blank*).
- **Switch to MySQL**: Simply uncomment the MySQL properties in `application.properties`:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
  spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
  spring.datasource.username=root
  spring.datasource.password=root
  spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
  ```
