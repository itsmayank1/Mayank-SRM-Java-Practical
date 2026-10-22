package com.example.controller;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public DashboardController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication != null ? authentication.getName() : "Guest");
        model.addAttribute("totalEmployees", employeeService.getTotalEmployees());
        model.addAttribute("activeEmployees", employeeService.getActiveEmployeesCount());
        model.addAttribute("totalDepartments", departmentService.getAllDepartments().size());
        model.addAttribute("totalPayroll", String.format("₹%,.2f", employeeService.getTotalPayroll()));
        model.addAttribute("averageSalary", String.format("₹%,.2f", employeeService.getAverageSalary()));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("recentEmployees", employeeService.getRecentEmployees(5));
        return "dashboard";
    }
}
