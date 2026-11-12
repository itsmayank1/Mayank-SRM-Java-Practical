package com.example;

import com.example.entity.Appointment;
import com.example.entity.Department;
import com.example.entity.Doctor;
import com.example.entity.Patient;
import com.example.repository.AppointmentRepository;
import com.example.repository.DepartmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HospitalRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void testInitialDataSeededSuccessfully() {
        assertTrue(departmentRepository.count() >= 5);
        assertTrue(doctorRepository.count() >= 6);
        assertTrue(patientRepository.count() >= 5);
        assertTrue(appointmentRepository.count() >= 5);
    }

    @Test
    void testDepartmentDoctorRelationship() {
        Optional<Department> cardioOpt = departmentRepository.findByNameIgnoreCase("Cardiology");
        assertTrue(cardioOpt.isPresent());

        List<Doctor> cardioDocs = doctorRepository.findByDepartmentId(cardioOpt.get().getId());
        assertFalse(cardioDocs.isEmpty());
        for (Doctor doc : cardioDocs) {
            assertEquals("Cardiology", doc.getDepartmentName());
        }
    }

    @Test
    void testPatientCreationAndSearch() {
        Patient p = new Patient("Test Patient", "test@hospital.com", "9998887776", 30, "Male", "O+", "Chennai");
        Patient saved = patientRepository.save(p);
        assertNotNull(saved.getId());

        List<Patient> found = patientRepository.findByNameContainingIgnoreCase("Test");
        assertFalse(found.isEmpty());
        assertEquals("Test Patient", found.get(0).getName());
    }

    @Test
    void testAppointmentBookingAndStatusUpdate() {
        Doctor doctor = doctorRepository.findAll().get(0);
        Patient patient = patientRepository.findAll().get(0);

        Appointment app = new Appointment(doctor, patient, "2026-11-10", "11:00 AM",
                "Follow up blood pressure test", "Scheduled", "OPD-105", 600.0);
        Appointment saved = appointmentRepository.save(app);

        assertNotNull(saved.getId());
        assertEquals("Scheduled", saved.getStatus());
        assertEquals(doctor.getId(), saved.getDoctor().getId());
        assertEquals(patient.getId(), saved.getPatient().getId());

        // Update status
        saved.setStatus("Completed");
        appointmentRepository.save(saved);

        Appointment reFetched = appointmentRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Completed", reFetched.getStatus());
    }
}
