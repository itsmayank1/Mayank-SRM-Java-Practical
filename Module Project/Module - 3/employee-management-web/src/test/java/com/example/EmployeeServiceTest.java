package com.example;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private DepartmentService departmentService;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        departmentService = new DepartmentService();
        employeeService = new EmployeeService(departmentService);
        employeeService.init();
    }

    @Test
    void testInitialDataSeededInArrayList() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        assertFalse(allEmployees.isEmpty());
        assertTrue(allEmployees.size() >= 6);
    }

    @Test
    void testDepartmentRelationshipAndSync() {
        List<Department> departments = departmentService.getAllDepartments();
        assertFalse(departments.isEmpty());

        Optional<Department> engDept = departments.stream()
                .filter(d -> "ENG".equals(d.getCode()))
                .findFirst();
        assertTrue(engDept.isPresent());
        assertTrue(engDept.get().getEmployeeCount() > 0);
    }

    @Test
    void testCreateAndReadEmployee() {
        Employee newEmp = new Employee(null, "Anil", "Kumar", "anil.k@example.com", "9123456789",
                "QA Engineer", 75000.0, 1L, null, "ROLE_EMPLOYEE", "2024-04-01", "Active");

        Employee saved = employeeService.saveEmployee(newEmp);
        assertNotNull(saved.getId());
        assertEquals("Software Engineering", saved.getDepartmentName());

        Optional<Employee> fetched = employeeService.getEmployeeById(saved.getId());
        assertTrue(fetched.isPresent());
        assertEquals("Anil Kumar", fetched.get().getFullName());
    }

    @Test
    void testUpdateEmployee() {
        Employee emp = employeeService.getAllEmployees().get(0);
        Long id = emp.getId();

        emp.setSalary(150000.0);
        emp.setStatus("On Leave");
        employeeService.saveEmployee(emp);

        Optional<Employee> updated = employeeService.getEmployeeById(id);
        assertTrue(updated.isPresent());
        assertEquals(150000.0, updated.get().getSalary());
        assertEquals("On Leave", updated.get().getStatus());
    }

    @Test
    void testDeleteEmployee() {
        Employee newEmp = new Employee(null, "Temp", "User", "temp@example.com", "0000000000",
                "Intern", 30000.0, 2L, null, "ROLE_EMPLOYEE", "2024-05-01", "Active");
        Employee saved = employeeService.saveEmployee(newEmp);
        Long id = saved.getId();

        boolean deleted = employeeService.deleteEmployee(id);
        assertTrue(deleted);
        assertFalse(employeeService.getEmployeeById(id).isPresent());
    }

    @Test
    void testSearchEmployees() {
        List<Employee> results = employeeService.searchEmployees("Mayank", null);
        assertFalse(results.isEmpty());
        assertEquals("Mayank Upadhyay", results.get(0).getFullName());

        List<Employee> deptResults = employeeService.searchEmployees(null, 1L);
        assertFalse(deptResults.isEmpty());
        for (Employee e : deptResults) {
            assertEquals(1L, e.getDepartmentId());
        }
    }

    @Test
    void testDashboardAnalytics() {
        assertTrue(employeeService.getTotalEmployees() > 0);
        assertTrue(employeeService.getTotalPayroll() > 0);
        assertTrue(employeeService.getAverageSalary() > 0);
    }
}
