package com.example.service;

import com.example.model.Department;
import com.example.model.Employee;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // Thread-safe ArrayList storage conforming to "Data Stored in ArrayList" requirement
    private final List<Employee> employeeList = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1000);
    private final DepartmentService departmentService;

    public EmployeeService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostConstruct
    public void init() {
        seedInitialEmployees();
        syncDepartmentCounts();
    }

    private void seedInitialEmployees() {
        saveEmployee(new Employee(101L, "Mayank", "Upadhyay", "mayank.u@example.com", "9876543210",
                "Senior Full Stack Engineer", 125000.0, 1L, "Software Engineering", "ROLE_ADMIN", "2023-01-15", "Active"));

        saveEmployee(new Employee(102L, "Aarav", "Sharma", "aarav.s@example.com", "9876543211",
                "Backend Specialist", 95000.0, 1L, "Software Engineering", "ROLE_EMPLOYEE", "2023-06-01", "Active"));

        saveEmployee(new Employee(103L, "Priya", "Nair", "priya.n@example.com", "9876543212",
                "Talent Acquisition Lead", 85000.0, 2L, "Human Resources", "ROLE_MANAGER", "2022-11-20", "Active"));

        saveEmployee(new Employee(104L, "Rohan", "Gupta", "rohan.g@example.com", "9876543213",
                "Financial Analyst", 92000.0, 3L, "Finance & Accounts", "ROLE_EMPLOYEE", "2023-08-10", "Active"));

        saveEmployee(new Employee(105L, "Sneha", "Patel", "sneha.p@example.com", "9876543214",
                "Creative Designer", 78000.0, 4L, "Product & Marketing", "ROLE_EMPLOYEE", "2024-02-14", "Active"));

        saveEmployee(new Employee(106L, "Vikram", "Iyer", "vikram.i@example.com", "9876543215",
                "DevOps Architect", 135000.0, 1L, "Software Engineering", "ROLE_MANAGER", "2022-04-18", "On Leave"));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        if (id == null) return Optional.empty();
        return employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Employee saveEmployee(Employee employee) {
        // Resolve Department Relationship
        if (employee.getDepartmentId() != null) {
            Optional<Department> deptOpt = departmentService.getDepartmentById(employee.getDepartmentId());
            deptOpt.ifPresent(department -> employee.setDepartmentName(department.getName()));
        }

        if (employee.getId() == null) {
            employee.setId(idGenerator.incrementAndGet());
            employeeList.add(employee);
        } else {
            boolean updated = false;
            for (int i = 0; i < employeeList.size(); i++) {
                if (employeeList.get(i).getId().equals(employee.getId())) {
                    employeeList.set(i, employee);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                employeeList.add(employee);
            }
        }
        syncDepartmentCounts();
        return employee;
    }

    public boolean deleteEmployee(Long id) {
        boolean removed = employeeList.removeIf(e -> e.getId().equals(id));
        if (removed) {
            syncDepartmentCounts();
        }
        return removed;
    }

    public List<Employee> searchEmployees(String keyword, Long departmentId) {
        return employeeList.stream()
                .filter(e -> {
                    boolean matchesKeyword = true;
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lower = keyword.trim().toLowerCase();
                        matchesKeyword = e.getFullName().toLowerCase().contains(lower)
                                || (e.getEmail() != null && e.getEmail().toLowerCase().contains(lower))
                                || (e.getDesignation() != null && e.getDesignation().toLowerCase().contains(lower))
                                || (e.getDepartmentName() != null && e.getDepartmentName().toLowerCase().contains(lower));
                    }
                    boolean matchesDept = true;
                    if (departmentId != null && departmentId > 0) {
                        matchesDept = e.getDepartmentId() != null && e.getDepartmentId().equals(departmentId);
                    }
                    return matchesKeyword && matchesDept;
                })
                .collect(Collectors.toList());
    }

    public void syncDepartmentCounts() {
        departmentService.recalculateEmployeeCounts(employeeList);
    }

    // Dashboard Analytics
    public int getTotalEmployees() {
        return employeeList.size();
    }

    public long getActiveEmployeesCount() {
        return employeeList.stream()
                .filter(e -> "Active".equalsIgnoreCase(e.getStatus()))
                .count();
    }

    public double getTotalPayroll() {
        return employeeList.stream()
                .mapToDouble(e -> e.getSalary() != null ? e.getSalary() : 0.0)
                .sum();
    }

    public double getAverageSalary() {
        if (employeeList.isEmpty()) return 0.0;
        return getTotalPayroll() / employeeList.size();
    }

    public List<Employee> getRecentEmployees(int limit) {
        return employeeList.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
