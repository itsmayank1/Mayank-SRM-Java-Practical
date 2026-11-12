package com.example.employee.model;

public enum Role {
    HR,
    MANAGER,
    EMPLOYEE;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
