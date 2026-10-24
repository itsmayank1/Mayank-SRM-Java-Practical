package com.example.service;

import com.example.model.Department;
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
        create(new Department(nextId.getAndIncrement(), "IT", "IT", "Tech Tower - Floor 4", "Dr. Rajesh Sharma"));
        create(new Department(nextId.getAndIncrement(), "HR", "HR", "Admin Block - Floor 2", "Pooja Verma"));
        create(new Department(nextId.getAndIncrement(), "Finance", "FIN", "Finance Wing - Floor 1", "Suresh Menon"));
        create(new Department(nextId.getAndIncrement(), "Marketing", "MKT", "Innovation Hub - Floor 3", "Ananya Roy"));
    }

    // Teacher compatibility methods
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

    // Extended service methods
    public List<Department> getAllDepartments() {
        return getAll();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return Optional.ofNullable(getById(id));
    }

    public Department saveDepartment(Department department) {
        if (department.getId() == null) {
            return create(department);
        } else {
            for (int i = 0; i < departments.size(); i++) {
                if (departments.get(i).getId().equals(department.getId())) {
                    departments.set(i, department);
                    return department;
                }
            }
            departments.add(department);
            return department;
        }
    }

    public boolean deleteDepartment(Long id) {
        return departments.removeIf(d -> d.getId().equals(id));
    }

    public boolean delete(Long id) {
        return deleteDepartment(id);
    }

    public void recalculateEmployeeCounts(List<com.example.model.Employee> employees) {
        for (Department dept : departments) {
            long count = employees.stream()
                    .filter(e -> e.getDepartment() != null && dept.getId().equals(e.getDepartment().getId()))
                    .count();
            dept.setEmployeeCount((int) count);
        }
    }
}
