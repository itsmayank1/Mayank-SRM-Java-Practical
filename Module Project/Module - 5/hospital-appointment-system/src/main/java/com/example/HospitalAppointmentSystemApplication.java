package com.example;

import com.example.entity.Appointment;
import com.example.entity.Department;
import com.example.entity.Doctor;
import com.example.entity.Patient;
import com.example.repository.AppointmentRepository;
import com.example.repository.DepartmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HospitalAppointmentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalAppointmentSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(DepartmentRepository departmentRepository,
                                          DoctorRepository doctorRepository,
                                          PatientRepository patientRepository,
                                          AppointmentRepository appointmentRepository) {
        return args -> {
            if (departmentRepository.count() == 0) {
                // 1. Seed Departments
                Department card = departmentRepository.save(new Department("Cardiology", "Cardiovascular care and heart surgery", "Wing A - 3rd Floor", "Dr. Rajesh Sharma"));
                Department neuro = departmentRepository.save(new Department("Neurology", "Comprehensive brain, spine, and nerve disorders", "Wing B - 4th Floor", "Dr. Neha Kapoor"));
                Department ortho = departmentRepository.save(new Department("Orthopedics", "Bone, joint reconstruction, and sports injuries", "Wing A - 1st Floor", "Dr. Vikram Sethi"));
                Department pedia = departmentRepository.save(new Department("Pediatrics", "Child care, neonatal therapy, and immunization", "Wing C - 2nd Floor", "Dr. Ananya Roy"));
                Department gen = departmentRepository.save(new Department("General Medicine", "Comprehensive outpatient primary care and diagnosis", "Ground Floor - OPD", "Dr. Amit Patel"));

                // 2. Seed Doctors
                Doctor d1 = doctorRepository.save(new Doctor("Rajesh Sharma", "rajesh.cardio@hospital.com", "9876540001", "Senior Interventional Cardiologist", "MD, DM (Cardio), FACC", "Mon, Wed, Fri", "09:00 AM - 01:00 PM", card));
                Doctor d2 = doctorRepository.save(new Doctor("Neha Kapoor", "neha.neuro@hospital.com", "9876540002", "Consultant Neurologist", "MD, DM (Neurology)", "Tue, Thu, Sat", "10:00 AM - 02:00 PM", neuro));
                Doctor d3 = doctorRepository.save(new Doctor("Vikram Sethi", "vikram.ortho@hospital.com", "9876540003", "Joint Replacement Surgeon", "MS (Ortho), M.Ch", "Mon, Tue, Thu", "02:00 PM - 06:00 PM", ortho));
                Doctor d4 = doctorRepository.save(new Doctor("Ananya Roy", "ananya.pedia@hospital.com", "9876540004", "Chief Pediatric Specialist", "MD (Pediatrics), DCH", "Mon to Sat", "09:00 AM - 01:00 PM", pedia));
                Doctor d5 = doctorRepository.save(new Doctor("Amit Patel", "amit.med@hospital.com", "9876540005", "Senior Physician", "MD (Internal Medicine)", "Daily", "08:00 AM - 02:00 PM", gen));
                Doctor d6 = doctorRepository.save(new Doctor("Sneha Rao", "sneha.cardio@hospital.com", "9876540006", "Pediatric Cardiologist", "DNB (Cardiology)", "Wed, Fri, Sat", "02:00 PM - 05:00 PM", card));

                // 3. Seed Patients
                Patient p1 = patientRepository.save(new Patient("Mayank Upadhyay", "mayank.u@example.com", "9876543210", 21, "Male", "B+", "SRM Nagar, Kattankulathur, Chennai"));
                Patient p2 = patientRepository.save(new Patient("Rahul Verma", "rahul.v@example.com", "9876543211", 34, "Male", "O+", "Velachery Main Road, Chennai"));
                Patient p3 = patientRepository.save(new Patient("Priya Sundaram", "priya.s@example.com", "9876543212", 28, "Female", "A+", "GST Road, Tambaram, Chennai"));
                Patient p4 = patientRepository.save(new Patient("Kavita Nair", "kavita.n@example.com", "9876543213", 48, "Female", "AB+", "Guindy Industrial Estate, Chennai"));
                Patient p5 = patientRepository.save(new Patient("Aarav Roy", "aarav.r@example.com", "9876543214", 8, "Male", "O-", "Anna Nagar West, Chennai"));

                // 4. Seed Appointments
                String today = LocalDate.now().toString();
                String tomorrow = LocalDate.now().plusDays(1).toString();
                String nextWeek = LocalDate.now().plusDays(4).toString();

                appointmentRepository.save(new Appointment(d1, p1, today, "10:30 AM", "Routine preventive cardiac checkup and ECG review", "Confirmed", "OPD-302", 800.0));
                appointmentRepository.save(new Appointment(d2, p4, today, "11:30 AM", "Persistent migraine headache and MRI consultation", "Scheduled", "OPD-405", 1000.0));
                appointmentRepository.save(new Appointment(d3, p2, tomorrow, "03:00 PM", "Right knee cartilage follow-up and X-ray evaluation", "Confirmed", "OPD-108", 750.0));
                appointmentRepository.save(new Appointment(d4, p5, tomorrow, "10:00 AM", "Annual seasonal vaccination and growth assessment", "Scheduled", "OPD-204", 600.0));
                appointmentRepository.save(new Appointment(d5, p3, nextWeek, "09:15 AM", "Viral fever checkup and full blood count analysis", "Confirmed", "OPD-012", 500.0));
            }
        };
    }
}
