# Module - 4: Spring Data JPA & Thymeleaf Web Mini-Project

**Student**: Mayank Upadhyay  
**Course**: SRM-IST CSE Core Java Full Stack Practical  
**Department**: Computer Science and Engineering (CSE Core)

---

## 📂 Module Structure

```
Module - 4/
└── hospital-appointment-system/
    ├── pom.xml
    ├── README.md
    └── src/
        ├── main/
        │   ├── java/com/example/
        │   │   ├── HospitalAppointmentSystemApplication.java
        │   │   ├── controller/HospitalWebController.java
        │   │   ├── entity/ (Department, Doctor, Patient, Appointment)
        │   │   └── repository/ (Spring Data JPA Repositories)
        │   └── resources/
        │       ├── application.properties
        │       ├── static/ (style.css)
        │       └── templates/ (index, departments, doctors, appointments, patients)
        └── test/java/com/example/ (12 Automated Unit & Integration Tests)
```

---

## 🏥 Main Project: `hospital-appointment-system`

**Hospital Appointment System — Mini Project With HTML/CSS/Thymeleaf**
- **Architecture**: Spring Boot 3.3.4 + Spring MVC + Spring Data JPA (Hibernate) + MySQL / H2 + Thymeleaf.
- **Relational Model**:
  - `Department`: Clinical wings, locations, and head physicians (`@OneToMany` with Doctors).
  - `Doctor`: Specialists with consulting hours, qualifications, and department affiliations.
  - `Patient`: Medical profiles with age, gender, blood group, contact, and address.
  - `Appointment`: Relational scheduling connecting Doctors and Patients with room assignments and fees.
- **Web Interface**:
  - `index.html`: Dashboard with real-time KPI metrics, today's schedule, and quick appointment booking.
  - `departments.html`: Department management and physician counts.
  - `doctors.html`: Specialist directory with search and department filtering.
  - `patients.html`: Patient registration and medical records directory.
  - `appointments.html`: Outpatient desk with scheduling, status updates (`Confirmed`, `Completed`, `Cancelled`), and date/doctor filters.

**Quick Run**:
```powershell
cd hospital-appointment-system
.\mvnw.cmd spring-boot:run
```
*Access via browser: `http://localhost:8080`*
