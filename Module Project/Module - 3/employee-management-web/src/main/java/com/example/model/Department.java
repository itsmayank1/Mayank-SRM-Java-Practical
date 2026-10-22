package com.example.model;

public class Department {
    private Long id;
    private String name;
    private String code;
    private String location;
    private String headOfDepartment;
    private int employeeCount;

    public Department() {
    }

    public Department(Long id, String name, String code, String location, String headOfDepartment) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.location = location;
        this.headOfDepartment = headOfDepartment;
        this.employeeCount = 0;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", location='" + location + '\'' +
                ", headOfDepartment='" + headOfDepartment + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
