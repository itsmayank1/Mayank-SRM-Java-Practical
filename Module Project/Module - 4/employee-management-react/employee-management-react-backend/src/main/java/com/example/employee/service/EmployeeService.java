package com.example.employee.service;

import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.model.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final List<Employee> employees = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);
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
        Department it = departmentService.getById(1L);
        Department hr = departmentService.getById(2L);
        Department fin = departmentService.getById(3L);
        Department mkt = departmentService.getById(4L);
        Department ops = departmentService.getById(5L);

        create(new Employee(nextId.getAndIncrement(), "Mayank Upadhyay", "mayank.u@example.com", 125000.0, it,
                "9876543210", "Full Stack Architect", Role.HR, "2023-01-15", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Ravi Kumar", "ravi.kumar@example.com", 85000.0, it,
                "9876543211", "Senior Frontend Engineer", Role.EMPLOYEE, "2023-06-01", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Priya Sharma", "priya.sharma@example.com", 65000.0, hr,
                "9876543212", "Talent Acquisition Lead", Role.MANAGER, "2022-11-20", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Amit Patel", "amit.patel@example.com", 72000.0, it,
                "9876543213", "Cloud Infrastructure Engineer", Role.EMPLOYEE, "2023-08-10", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Sneha Verma", "sneha.verma@example.com", 58000.0, fin,
                "9876543214", "Financial Analyst", Role.EMPLOYEE, "2024-02-14", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Rajesh Gupta", "rajesh.gupta@example.com", 54000.0, mkt,
                "9876543215", "Digital Campaign Specialist", Role.EMPLOYEE, "2022-04-18", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Anita Desai", "anita.desai@example.com", 62000.0, ops,
                "9876543216", "Supply Chain Coordinator", Role.EMPLOYEE, "2024-03-01", "Active"));
    }

    public Employee create(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(nextId.getAndIncrement());
        }
        employees.add(employee);
        syncDepartmentCounts();
        return employee;
    }

    public Employee getById(Long id) {
        if (id == null) return null;
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployees(int page, int size, String sortBy, String direction) {
        List<Employee> list = new ArrayList<>(employees);

        Comparator<Employee> comparator;
        if ("salary".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparingDouble(Employee::getSalary);
        } else if ("email".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Employee::getEmail, String.CASE_INSENSITIVE_ORDER);
        } else if ("department".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(e -> e.getDepartment() != null ? e.getDepartment().getName() : "", String.CASE_INSENSITIVE_ORDER);
        } else {
            comparator = Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }
        list.sort(comparator);

        int start = Math.max(0, page * size);
        if (start >= list.size()) {
            return new ArrayList<>();
        }
        int end = Math.min(start + size, list.size());
        return list.subList(start, end);
    }

    public Employee update(Long id, Employee updated) {
        Employee existing = getById(id);
        if (existing == null) {
            return null;
        }
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setSalary(updated.getSalary());
        if (updated.getDepartment() != null) {
            existing.setDepartment(updated.getDepartment());
        }
        if (updated.getPhone() != null) existing.setPhone(updated.getPhone());
        if (updated.getDesignation() != null) existing.setDesignation(updated.getDesignation());
        if (updated.getRole() != null) existing.setRole(updated.getRole());
        if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
        if (updated.getJoiningDate() != null) existing.setJoiningDate(updated.getJoiningDate());

        syncDepartmentCounts();
        return existing;
    }

    public boolean delete(Long id) {
        boolean removed = employees.removeIf(e -> e.getId().equals(id));
        if (removed) {
            syncDepartmentCounts();
        }
        return removed;
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public void syncDepartmentCounts() {
        departmentService.recalculateEmployeeCounts(employees);
    }
}
