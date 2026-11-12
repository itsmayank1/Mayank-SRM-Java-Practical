package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String appointmentDate;

    @Column(nullable = false)
    private String appointmentTime;

    private String reason;

    @Column(nullable = false)
    private String status; // Scheduled, Confirmed, Completed, Cancelled

    private String roomNumber;

    private Double consultationFee;

    public Appointment() {
        this.status = "Scheduled";
    }

    public Appointment(Doctor doctor, Patient patient, String appointmentDate, String appointmentTime,
                       String reason, String status, String roomNumber, Double consultationFee) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = (status != null && !status.isBlank()) ? status : "Scheduled";
        this.roomNumber = roomNumber;
        this.consultationFee = consultationFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getDoctorName() {
        return doctor != null ? doctor.getName() : "Unassigned";
    }

    public String getPatientName() {
        return patient != null ? patient.getName() : "Unknown";
    }

    public String getDepartmentName() {
        return (doctor != null && doctor.getDepartment() != null) ? doctor.getDepartment().getName() : "General";
    }

    @Override
    public String toString() {
        return "Appointment #" + id + " [" + appointmentDate + " " + appointmentTime + "] - " + getDoctorName() + " with " + getPatientName();
    }
}
