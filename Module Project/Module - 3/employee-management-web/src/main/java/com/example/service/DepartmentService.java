package com.example.service;

import com.example.model.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DepartmentService {

    // Thread-safe ArrayList storage as specified in project requirements
    private final List<Department> departmentList = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(100);

    public DepartmentService() {
        seedInitialDepartments();
    }

    private void seedInitialDepartments() {
        departmentList.add(new Department(1L, "Software Engineering", "ENG", "Tech Tower - Floor 4", "Dr. Rajesh Sharma"));
        departmentList.add(new Department(2L, "Human Resources", "HR", "Admin Block - Floor 2", "Pooja Verma"));
        departmentList.add(new Department(3L, "Finance & Accounts", "FIN", "Finance Wing - Floor 1", "Suresh Menon"));
        departmentList.add(new Department(4L, "Product & Marketing", "MKT", "Innovation Hub - Floor 3", "Ananya Roy"));
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departmentList);
    }

    public Optional<Department> getDepartmentById(Long id) {
        if (id == null) return Optional.empty();
        return departmentList.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public Department saveDepartment(Department department) {
        if (department.getId() == null) {
            department.setId(idGenerator.incrementAndGet());
            departmentList.add(department);
            return department;
        } else {
            for (int i = 0; i < departmentList.size(); i++) {
                if (departmentList.get(i).getId().equals(department.getId())) {
                    departmentList.set(i, department);
                    return department;
                }
            }
            departmentList.add(department);
            return department;
        }
    }

    public boolean deleteDepartment(Long id) {
        return departmentList.removeIf(d -> d.getId().equals(id));
    }

    public void recalculateEmployeeCounts(List<com.example.model.Employee> employees) {
        for (Department dept : departmentList) {
            long count = employees.stream()
                    .filter(e -> e.getDepartmentId() != null && e.getDepartmentId().equals(dept.getId()))
                    .count();
            dept.setEmployeeCount((int) count);
        }
    }
}
