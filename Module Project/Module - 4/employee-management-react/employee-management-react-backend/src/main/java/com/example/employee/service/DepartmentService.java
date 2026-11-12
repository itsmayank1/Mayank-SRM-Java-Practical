package com.example.employee.service;

import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DepartmentService {

    private final List<Department> departments = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public DepartmentService() {
        seedInitialDepartments();
    }

    private void seedInitialDepartments() {
        create(new Department(nextId.getAndIncrement(), "Engineering & IT", "IT", "Tech Tower - Floor 4", "Dr. Rajesh Sharma"));
        create(new Department(nextId.getAndIncrement(), "Human Resources", "HR", "Admin Block - Floor 2", "Pooja Verma"));
        create(new Department(nextId.getAndIncrement(), "Finance & Accounts", "FIN", "Finance Wing - Floor 1", "Suresh Menon"));
        create(new Department(nextId.getAndIncrement(), "Marketing & Sales", "MKT", "Innovation Hub - Floor 3", "Ananya Roy"));
        create(new Department(nextId.getAndIncrement(), "Operations & Logistics", "OPS", "Main Campus - Block C", "Kunal Singhal"));
    }

    public List<Department> getAll() {
        return new ArrayList<>(departments);
    }

    public Department getById(Long id) {
        if (id == null) return null;
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Department create(Department department) {
        if (department.getId() == null) {
            department.setId(nextId.getAndIncrement());
        }
        departments.add(department);
        return department;
    }

    public List<Department> getAllDepartments() {
        return getAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return Optional.ofNullable(getById(id));
    }

    public void recalculateEmployeeCounts(List<Employee> employeeList) {
        for (Department dept : departments) {
            long count = employeeList.stream()
                    .filter(e -> e.getDepartment() != null && dept.getId().equals(e.getDepartment().getId()))
                    .count();
            dept.setEmployeeCount((int) count);
        }
    }
}
