package com.example.service;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.model.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // Thread-safe ArrayList storage as specified in project requirements
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

        create(new Employee(nextId.getAndIncrement(), "Mayank Upadhyay", "mayank.u@example.com", 125000.0, it,
                "9876543210", "Lead Architect", Role.HR, "2023-01-15", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Aarav Sharma", "aarav.s@example.com", 95000.0, it,
                "9876543211", "Backend Specialist", Role.EMPLOYEE, "2023-06-01", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Priya Nair", "priya.n@example.com", 85000.0, hr,
                "9876543212", "Talent Acquisition Lead", Role.MANAGER, "2022-11-20", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Rohan Gupta", "rohan.g@example.com", 92000.0, fin,
                "9876543213", "Financial Analyst", Role.EMPLOYEE, "2023-08-10", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Sneha Patel", "sneha.p@example.com", 78000.0, mkt,
                "9876543214", "Creative Designer", Role.EMPLOYEE, "2024-02-14", "Active"));

        create(new Employee(nextId.getAndIncrement(), "Vikram Iyer", "vikram.i@example.com", 135000.0, it,
                "9876543215", "DevOps Engineer", Role.MANAGER, "2022-04-18", "On Leave"));

        create(new Employee(nextId.getAndIncrement(), "Ananya Sen", "ananya.s@example.com", 88000.0, hr,
                "9876543216", "HR Executive", Role.EMPLOYEE, "2024-03-01", "Active"));
    }

    // Teacher compatibility methods
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
        return filterAndSortEmployees(null, null, page, size, sortBy, direction);
    }

    public int getTotalPages(int size) {
        return getTotalPages(null, null, size);
    }

    public int getTotalPages(String keyword, Long departmentId, int size) {
        List<Employee> filtered = filterList(keyword, departmentId);
        if (filtered.isEmpty()) {
            return 1;
        }
        return (int) Math.ceil((double) filtered.size() / size);
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

    // Extended search, filter, and pagination
    public List<Employee> filterAndSortEmployees(String keyword, Long departmentId,
                                                int page, int size, String sortBy, String direction) {
        List<Employee> result = filterList(keyword, departmentId);

        Comparator<Employee> comparator;
        if ("salary".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparingDouble(Employee::getSalary);
        } else if ("email".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Employee::getEmail, String.CASE_INSENSITIVE_ORDER);
        } else if ("id".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(Employee::getId);
        } else {
            comparator = Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }
        result.sort(comparator);

        int start = Math.max(0, page * size);
        if (start >= result.size()) {
            return new ArrayList<>();
        }
        int end = Math.min(start + size, result.size());
        return result.subList(start, end);
    }

    private List<Employee> filterList(String keyword, Long departmentId) {
        return employees.stream()
                .filter(e -> {
                    boolean matchesKeyword = true;
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lower = keyword.trim().toLowerCase();
                        matchesKeyword = (e.getName() != null && e.getName().toLowerCase().contains(lower))
                                || (e.getEmail() != null && e.getEmail().toLowerCase().contains(lower))
                                || (e.getDesignation() != null && e.getDesignation().toLowerCase().contains(lower))
                                || (e.getDepartment() != null && e.getDepartment().getName().toLowerCase().contains(lower));
                    }
                    boolean matchesDept = true;
                    if (departmentId != null && departmentId > 0) {
                        matchesDept = e.getDepartment() != null && departmentId.equals(e.getDepartment().getId());
                    }
                    return matchesKeyword && matchesDept;
                })
                .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.ofNullable(getById(id));
    }

    public Employee saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            return create(employee);
        } else {
            return update(employee.getId(), employee);
        }
    }

    public boolean deleteEmployee(Long id) {
        return delete(id);
    }

    public List<Employee> searchEmployees(String keyword, Long departmentId) {
        return filterList(keyword, departmentId);
    }

    public void syncDepartmentCounts() {
        departmentService.recalculateEmployeeCounts(employees);
    }

    // Analytics & Metrics
    public int getTotalEmployees() {
        return employees.size();
    }

    public long getActiveEmployeesCount() {
        return employees.stream()
                .filter(e -> "Active".equalsIgnoreCase(e.getStatus()))
                .count();
    }

    public double getTotalPayroll() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double getAverageSalary() {
        if (employees.isEmpty()) return 0.0;
        return getTotalPayroll() / employees.size();
    }

    public List<Employee> getRecentEmployees(int limit) {
        return employees.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public String generateCsvContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Name,Email,Department,Designation,Salary,Status\n");
        for (Employee e : employees) {
            sb.append(e.getId()).append(",")
              .append("\"").append(e.getName()).append("\",")
              .append(e.getEmail()).append(",")
              .append(e.getDepartment() != null ? e.getDepartment().getName() : "Unassigned").append(",")
              .append(e.getDesignation()).append(",")
              .append(e.getSalary()).append(",")
              .append(e.getStatus()).append("\n");
        }
        return sb.toString();
    }
}
