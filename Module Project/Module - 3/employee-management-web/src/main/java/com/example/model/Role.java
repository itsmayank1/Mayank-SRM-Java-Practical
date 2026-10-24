package com.example.model;

public enum Role {
    HR,
    MANAGER,
    EMPLOYEE;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
