package com.example;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        assertTrue(allEmployees.size() >= 7);
    }

    @Test
    void testDepartmentRelationshipAndSync() {
        List<Department> departments = departmentService.getAll();
        assertFalse(departments.isEmpty());

        Department itDept = departmentService.getById(1L);
        assertNotNull(itDept);
        assertTrue(itDept.getEmployeeCount() > 0);
    }

    @Test
    void testCreateAndReadEmployee() {
        Department hrDept = departmentService.getById(2L);
        Employee newEmp = new Employee(null, "Anil Kumar", "anil.k@example.com", 75000.0, hrDept);

        Employee saved = employeeService.create(newEmp);
        assertNotNull(saved.getId());
        assertEquals("HR", saved.getDepartment().getName());

        Employee fetched = employeeService.getById(saved.getId());
        assertNotNull(fetched);
        assertEquals("Anil Kumar", fetched.getName());
    }

    @Test
    void testPaginationAndSorting() {
        List<Employee> page0 = employeeService.getEmployees(0, 3, "salary", "desc");
        assertEquals(3, page0.size());
        assertTrue(page0.get(0).getSalary() >= page0.get(1).getSalary());

        int totalPages = employeeService.getTotalPages(3);
        assertTrue(totalPages >= 3);
    }

    @Test
    void testUpdateEmployee() {
        Employee emp = employeeService.getAllEmployees().get(0);
        Long id = emp.getId();

        emp.setSalary(150000.0);
        emp.setStatus("On Leave");
        employeeService.update(id, emp);

        Employee updated = employeeService.getById(id);
        assertNotNull(updated);
        assertEquals(150000.0, updated.getSalary());
        assertEquals("On Leave", updated.getStatus());
    }

    @Test
    void testDeleteEmployee() {
        Department it = departmentService.getById(1L);
        Employee newEmp = new Employee(null, "Temp User", "temp@example.com", 30000.0, it);
        Employee saved = employeeService.create(newEmp);
        Long id = saved.getId();

        boolean deleted = employeeService.delete(id);
        assertTrue(deleted);
        assertNull(employeeService.getById(id));
    }

    @Test
    void testCsvExportGeneration() {
        String csv = employeeService.generateCsvContent();
        assertNotNull(csv);
        assertTrue(csv.contains("Mayank Upadhyay"));
        assertTrue(csv.contains("ID,Name,Email,Department"));
    }
}
