package com.example.controller;

import com.example.entity.Appointment;
import com.example.entity.Department;
import com.example.entity.Doctor;
import com.example.entity.Patient;
import com.example.repository.AppointmentRepository;
import com.example.repository.DepartmentRepository;
import com.example.repository.DoctorRepository;
import com.example.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HospitalWebController {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public HospitalWebController(DepartmentRepository departmentRepository,
                                  DoctorRepository doctorRepository,
                                  PatientRepository patientRepository,
                                  AppointmentRepository appointmentRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // ==========================================
    // 1. DASHBOARD / HOME (index.html)
    // ==========================================
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        String today = LocalDate.now().toString();

        model.addAttribute("totalDepartments", departmentRepository.count());
        model.addAttribute("totalDoctors", doctorRepository.count());
        model.addAttribute("totalPatients", patientRepository.count());
        model.addAttribute("totalAppointments", appointmentRepository.count());

        List<Appointment> allAppointments = appointmentRepository.findAll();
        List<Appointment> todayAppointments = allAppointments.stream()
                .filter(a -> today.equals(a.getAppointmentDate()))
                .collect(Collectors.toList());

        List<Appointment> recentAppointments = allAppointments.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("recentAppointments", recentAppointments);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("newAppointment", new Appointment());

        return "index";
    }

    // ==========================================
    // 2. DEPARTMENTS (departments.html)
    // ==========================================
    @GetMapping("/departments")
    public String viewDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("newDepartment", new Department());
        return "departments";
    }

    @PostMapping("/departments/save")
    public String saveDepartment(@ModelAttribute("newDepartment") Department department,
                                 RedirectAttributes redirectAttributes) {
        departmentRepository.save(department);
        redirectAttributes.addFlashAttribute("successMessage",
                "Department '" + department.getName() + "' saved successfully!");
        return "redirect:/departments";
    }

    @GetMapping("/departments/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Department> deptOpt = departmentRepository.findById(id);
        if (deptOpt.isPresent()) {
            Department dept = deptOpt.get();
            // Disassociate doctors before removing department
            for (Doctor d : dept.getDoctors()) {
                d.setDepartment(null);
                doctorRepository.save(d);
            }
            departmentRepository.delete(dept);
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found.");
        }
        return "redirect:/departments";
    }

    // ==========================================
    // 3. DOCTORS (doctors.html)
    // ==========================================
    @GetMapping("/doctors")
    public String viewDoctors(@RequestParam(value = "deptId", required = false) Long deptId,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              Model model) {
        List<Doctor> doctors = doctorRepository.findAll();

        if (deptId != null && deptId > 0) {
            doctors = doctors.stream()
                    .filter(d -> d.getDepartment() != null && d.getDepartment().getId().equals(deptId))
                    .collect(Collectors.toList());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lower = keyword.trim().toLowerCase();
            doctors = doctors.stream()
                    .filter(d -> d.getName().toLowerCase().contains(lower)
                            || (d.getSpecialization() != null && d.getSpecialization().toLowerCase().contains(lower))
                            || (d.getDepartmentName().toLowerCase().contains(lower)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("selectedDeptId", deptId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("newDoctor", new Doctor());
        return "doctors";
    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute("newDoctor") Doctor doctor,
                             @RequestParam(value = "departmentId", required = false) Long departmentId,
                             RedirectAttributes redirectAttributes) {
        if (departmentId != null) {
            departmentRepository.findById(departmentId).ifPresent(doctor::setDepartment);
        }
        doctorRepository.save(doctor);
        redirectAttributes.addFlashAttribute("successMessage",
                "Dr. " + doctor.getName() + " saved successfully!");
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Doctor profile removed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Doctor record not found.");
        }
        return "redirect:/doctors";
    }

    // ==========================================
    // 4. PATIENTS (patients.html)
    // ==========================================
    @GetMapping("/patients")
    public String viewPatients(@RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        List<Patient> patients = patientRepository.findAll();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lower = keyword.trim().toLowerCase();
            patients = patients.stream()
                    .filter(p -> p.getName().toLowerCase().contains(lower)
                            || (p.getPhone() != null && p.getPhone().contains(lower))
                            || (p.getEmail() != null && p.getEmail().toLowerCase().contains(lower)))
                    .collect(Collectors.toList());
        }

        model.addAttribute("patients", patients);
        model.addAttribute("keyword", keyword);
        model.addAttribute("newPatient", new Patient());
        return "patients";
    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute("newPatient") Patient patient,
                              RedirectAttributes redirectAttributes) {
        patientRepository.save(patient);
        redirectAttributes.addFlashAttribute("successMessage",
                "Patient '" + patient.getName() + "' registered successfully!");
        return "redirect:/patients";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Patient record deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
        }
        return "redirect:/patients";
    }

    // ==========================================
    // 5. APPOINTMENTS (appointments.html)
    // ==========================================
    @GetMapping("/appointments")
    public String viewAppointments(@RequestParam(value = "status", required = false) String status,
                                   @RequestParam(value = "doctorId", required = false) Long doctorId,
                                   @RequestParam(value = "date", required = false) String date,
                                   Model model) {
        List<Appointment> appointments = appointmentRepository.findAll();

        if (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status)) {
            appointments = appointments.stream()
                    .filter(a -> status.equalsIgnoreCase(a.getStatus()))
                    .collect(Collectors.toList());
        }

        if (doctorId != null && doctorId > 0) {
            appointments = appointments.stream()
                    .filter(a -> a.getDoctor() != null && doctorId.equals(a.getDoctor().getId()))
                    .collect(Collectors.toList());
        }

        if (date != null && !date.trim().isEmpty()) {
            appointments = appointments.stream()
                    .filter(a -> date.equals(a.getAppointmentDate()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedDoctorId", doctorId);
        model.addAttribute("selectedDate", date);
        model.addAttribute("newAppointment", new Appointment());
        return "appointments";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@ModelAttribute("newAppointment") Appointment appointment,
                                  @RequestParam("doctorId") Long doctorId,
                                  @RequestParam("patientId") Long patientId,
                                  RedirectAttributes redirectAttributes) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (doctorOpt.isPresent() && patientOpt.isPresent()) {
            appointment.setDoctor(doctorOpt.get());
            appointment.setPatient(patientOpt.get());

            if (appointment.getConsultationFee() == null || appointment.getConsultationFee() <= 0) {
                appointment.setConsultationFee(650.0);
            }
            if (appointment.getRoomNumber() == null || appointment.getRoomNumber().isBlank()) {
                appointment.setRoomNumber("OPD-101");
            }
            if (appointment.getStatus() == null || appointment.getStatus().isBlank()) {
                appointment.setStatus("Confirmed");
            }

            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Appointment booked successfully with Dr. " + doctorOpt.get().getName() + " for " + patientOpt.get().getName() + "!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Doctor or Patient not found.");
        }
        return "redirect:/appointments";
    }

    @GetMapping("/appointments/status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") String status,
                               RedirectAttributes redirectAttributes) {
        Optional<Appointment> appOpt = appointmentRepository.findById(id);
        if (appOpt.isPresent()) {
            Appointment appointment = appOpt.get();
            appointment.setStatus(status);
            appointmentRepository.save(appointment);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Appointment #" + id + " marked as " + status + ".");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
        }
        return "redirect:/appointments";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Appointment #" + id + " cancelled & removed.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment record not found.");
        }
        return "redirect:/appointments";
    }

    // ==========================================
    // 6. REST API Endpoints (For Automated Verification & Curl)
    // ==========================================
    @GetMapping("/api/stats")
    @ResponseBody
    public Map<String, Object> getHospitalStatsApi() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalDepartments", departmentRepository.count());
        stats.put("totalDoctors", doctorRepository.count());
        stats.put("totalPatients", patientRepository.count());
        stats.put("totalAppointments", appointmentRepository.count());
        return stats;
    }

    @GetMapping("/api/departments")
    @ResponseBody
    public List<Department> getAllDepartmentsApi() {
        return departmentRepository.findAll();
    }

    @GetMapping("/api/doctors")
    @ResponseBody
    public List<Doctor> getAllDoctorsApi() {
        return doctorRepository.findAll();
    }

    @GetMapping("/api/patients")
    @ResponseBody
    public List<Patient> getAllPatientsApi() {
        return patientRepository.findAll();
    }

    @GetMapping("/api/appointments")
    @ResponseBody
    public List<Appointment> getAllAppointmentsApi() {
        return appointmentRepository.findAll();
    }
}
