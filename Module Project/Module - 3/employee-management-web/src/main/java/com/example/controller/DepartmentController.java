package com.example.controller;

import com.example.model.Department;
import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String departments(Model model, HttpSession session) {
        employeeService.syncDepartmentCounts();
        Object roleObj = session.getAttribute("role");
        String role = roleObj != null ? roleObj.toString() : "HR";

        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("role", role);
        model.addAttribute("newDepartment", new Department());
        return "departments";
    }

    @PostMapping("/save")
    public String save(@RequestParam(value = "name", required = false) String name,
                       @ModelAttribute("newDepartment") Department departmentForm,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {

        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role)) {
            return "redirect:/departments";
        }

        String deptName = (departmentForm != null && departmentForm.getName() != null && !departmentForm.getName().isBlank())
                ? departmentForm.getName()
                : name;

        if (deptName != null && !deptName.isBlank()) {
            Department dept = new Department(null, deptName);
            if (departmentForm != null) {
                if (departmentForm.getCode() != null && !departmentForm.getCode().isBlank()) {
                    dept.setCode(departmentForm.getCode().toUpperCase());
                }
                if (departmentForm.getLocation() != null && !departmentForm.getLocation().isBlank()) {
                    dept.setLocation(departmentForm.getLocation());
                }
                if (departmentForm.getHeadOfDepartment() != null && !departmentForm.getHeadOfDepartment().isBlank()) {
                    dept.setHeadOfDepartment(departmentForm.getHeadOfDepartment());
                }
            }
            departmentService.create(dept);
            employeeService.syncDepartmentCounts();
            redirectAttributes.addFlashAttribute("successMessage", "Department '" + deptName + "' added successfully!");
        }

        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("role");
        if (role != null && !"HR".equalsIgnoreCase(role)) {
            return "redirect:/departments";
        }

        boolean deleted = departmentService.delete(id);
        if (deleted) {
            employeeService.syncDepartmentCounts();
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Department not found.");
        }
        return "redirect:/departments";
    }

    // REST API Endpoints
    @GetMapping("/api")
    @ResponseBody
    public List<Department> getAllDepartmentsApi() {
        employeeService.syncDepartmentCounts();
        return departmentService.getAll();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Department> getDepartmentByIdApi(@PathVariable Long id) {
        Department dept = departmentService.getById(id);
        return dept != null ? ResponseEntity.ok(dept) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api")
    @ResponseBody
    public Department createDepartmentApi(@RequestBody Department department) {
        return departmentService.create(department);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDepartmentApi(@PathVariable Long id) {
        boolean deleted = departmentService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
