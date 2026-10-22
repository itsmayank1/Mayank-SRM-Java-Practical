package com.example.controller;

import com.example.model.Department;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    // Web Views
    @GetMapping("/departments")
    public String viewDepartments(Model model) {
        employeeService.syncDepartmentCounts();
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("newDepartment", new Department());
        return "department";
    }

    @PostMapping("/departments/save")
    public String saveDepartment(@ModelAttribute("newDepartment") Department department,
                                 RedirectAttributes redirectAttributes) {
        departmentService.saveDepartment(department);
        employeeService.syncDepartmentCounts();
        redirectAttributes.addFlashAttribute("successMessage",
                "Department '" + department.getName() + "' saved successfully!");
        return "redirect:/departments";
    }

    @GetMapping("/departments/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        boolean deleted = departmentService.deleteDepartment(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found.");
        }
        return "redirect:/departments";
    }

    // REST API Endpoints
    @GetMapping("/api/departments")
    @ResponseBody
    public List<Department> getAllDepartmentsApi() {
        employeeService.syncDepartmentCounts();
        return departmentService.getAllDepartments();
    }

    @GetMapping("/api/departments/{id}")
    @ResponseBody
    public ResponseEntity<Department> getDepartmentByIdApi(@PathVariable Long id) {
        return departmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/departments")
    @ResponseBody
    public Department createDepartmentApi(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    @DeleteMapping("/api/departments/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDepartmentApi(@PathVariable Long id) {
        boolean deleted = departmentService.deleteDepartment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
