package com.example.controller;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String employees(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(defaultValue = "name") String sortBy,
                            @RequestParam(defaultValue = "asc") String direction,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "deptId", required = false) Long deptId,
                            HttpSession session,
                            Model model) {

        Object roleObj = session.getAttribute("role");
        String role = roleObj != null ? roleObj.toString() : "HR";

        List<Employee> employeeList = employeeService.filterAndSortEmployees(
                keyword, deptId, page, size, sortBy, direction
        );

        int totalPages = employeeService.getTotalPages(keyword, deptId, size);

        model.addAttribute("employees", employeeList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDeptId", deptId);
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("role", role);

        return "employees";
    }

    @GetMapping("/new")
    public String newEmployee(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role)) {
            return "redirect:/employees";
        }

        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("pageTitle", "Add New Employee");
        return "employee-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee,
                       @RequestParam(value = "departmentId", required = false) Long departmentId,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {

        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role)) {
            return "redirect:/employees";
        }

        if (departmentId != null) {
            Department dept = departmentService.getById(departmentId);
            employee.setDepartment(dept);
        }

        employeeService.create(employee);
        redirectAttributes.addFlashAttribute("successMessage",
                "Employee '" + employee.getName() + "' created successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role) && !"MANAGER".equalsIgnoreCase(role)) {
            return "redirect:/employees";
        }

        Employee employee = employeeService.getById(id);
        if (employee == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
            return "redirect:/employees";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("pageTitle", "Edit Employee #" + id);
        return "employee-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Employee employee,
                         @RequestParam(value = "departmentId", required = false) Long departmentId,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role) && !"MANAGER".equalsIgnoreCase(role)) {
            return "redirect:/employees";
        }

        if (departmentId != null) {
            Department dept = departmentService.getById(departmentId);
            employee.setDepartment(dept);
        }

        employeeService.update(id, employee);
        redirectAttributes.addFlashAttribute("successMessage",
                "Employee '" + employee.getName() + "' updated successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role)) {
            return "redirect:/employees";
        }

        boolean deleted = employeeService.delete(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
        }
        return "redirect:/employees";
    }

    // Bonus Enhancement: CSV Export of Roster
    @GetMapping("/export")
    public void exportEmployeesToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_export.csv");
        response.getWriter().write(employeeService.generateCsvContent());
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public List<Employee> getAllEmployeesApi() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeByIdApi(@PathVariable Long id) {
        Employee emp = employeeService.getById(id);
        return emp != null ? ResponseEntity.ok(emp) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api")
    @ResponseBody
    public Employee createEmployeeApi(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployeeApi(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = employeeService.update(id, employee);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEmployeeApi(@PathVariable Long id) {
        boolean deleted = employeeService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
