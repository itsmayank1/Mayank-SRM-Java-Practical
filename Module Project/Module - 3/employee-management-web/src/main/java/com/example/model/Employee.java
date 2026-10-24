package com.example.model;

public class Employee {
    private Long id;
    private String name;
    private String email;
    private double salary;
    private Department department;
    private String phone;
    private String designation;
    private Role role;
    private String joiningDate;
    private String status;

    public Employee() {
        this.status = "Active";
        this.role = Role.EMPLOYEE;
    }

    public Employee(Long id, String name, String email, double salary, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.phone = "9876543210";
        this.designation = "Associate";
        this.role = Role.EMPLOYEE;
        this.joiningDate = "2024-01-15";
        this.status = "Active";
    }

    public Employee(Long id, String name, String email, double salary, Department department,
                    String phone, String designation, Role role, String joiningDate, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.phone = phone;
        this.designation = designation;
        this.role = role != null ? role : Role.EMPLOYEE;
        this.joiningDate = joiningDate;
        this.status = (status != null && !status.isBlank()) ? status : "Active";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return department != null ? department.getId() : null;
    }

    public String getDepartmentName() {
        return department != null ? department.getName() : "Unassigned";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", department=" + (department != null ? department.getName() : "None") +
                ", role=" + role +
                '}';
    }
}
