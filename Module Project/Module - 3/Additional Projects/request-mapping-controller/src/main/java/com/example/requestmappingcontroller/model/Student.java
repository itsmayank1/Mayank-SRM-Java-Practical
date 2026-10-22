package com.example.requestmappingcontroller.model;

public class Student {
    private Long id;
    private String name;
    private String regNo;
    private String department;
    private String email;

    public Student() {
    }

    public Student(Long id, String name, String regNo, String department, String email) {
        this.id = id;
        this.name = name;
        this.regNo = regNo;
        this.department = department;
        this.email = email;
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

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
