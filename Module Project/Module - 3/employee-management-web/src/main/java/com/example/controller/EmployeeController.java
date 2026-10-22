package com.example.controller;

import com.example.model.Department;
import com.example.model.Employee;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    // Web Views
    @GetMapping("/employees")
    public String viewEmployees(@RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "deptId", required = false) Long deptId,
                                Model model) {
        List<Employee> employees = employeeService.searchEmployees(keyword, deptId);
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedDeptId", deptId);
        return "employees";
    }

    @GetMapping("/employees/new")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("pageTitle", "Add New Employee");
        return "employee-form";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes) {
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage",
                "Employee '" + employee.getFullName() + "' saved successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {
        return employeeService.getEmployeeById(id)
                .map(emp -> {
                    model.addAttribute("employee", emp);
                    model.addAttribute("departments", departmentService.getAllDepartments());
                    model.addAttribute("pageTitle", "Edit Employee #" + id);
                    return "employee-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
                    return "redirect:/employees";
                });
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found.");
        }
        return "redirect:/employees";
    }

    // REST API Endpoints
    @GetMapping("/api/employees")
    @ResponseBody
    public List<Employee> getAllEmployeesApi(@RequestParam(value = "keyword", required = false) String keyword,
                                             @RequestParam(value = "deptId", required = false) Long deptId) {
        return employeeService.searchEmployees(keyword, deptId);
    }

    @GetMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeByIdApi(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/employees")
    @ResponseBody
    public Employee createEmployeeApi(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployeeApi(@PathVariable Long id, @RequestBody Employee employee) {
        if (!employeeService.getEmployeeById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        employee.setId(id);
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @DeleteMapping("/api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEmployeeApi(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
