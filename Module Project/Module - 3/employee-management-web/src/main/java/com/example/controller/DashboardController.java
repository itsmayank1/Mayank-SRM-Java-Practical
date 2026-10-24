package com.example.controller;

import com.example.service.DepartmentService;
import com.example.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
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
    public String dashboard(HttpSession session, Model model) {
        Object usernameObj = session.getAttribute("username");
        Object roleObj = session.getAttribute("role");

        String username = usernameObj != null ? usernameObj.toString() : "Admin";
        String role = roleObj != null ? roleObj.toString() : "HR";

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("totalEmployees", employeeService.getTotalEmployees());
        model.addAttribute("activeEmployees", employeeService.getActiveEmployeesCount());
        model.addAttribute("totalDepartments", departmentService.getAll().size());
        model.addAttribute("totalPayroll", String.format("₹%,.2f", employeeService.getTotalPayroll()));
        model.addAttribute("averageSalary", String.format("₹%,.2f", employeeService.getAverageSalary()));
        model.addAttribute("departments", departmentService.getAll());
        model.addAttribute("recentEmployees", employeeService.getRecentEmployees(5));

        return "dashboard";
    }
}
