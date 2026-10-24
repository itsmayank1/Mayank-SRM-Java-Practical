package com.example.controller;

import com.example.security.JwtService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final JwtService jwtService;

    public LoginController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        if (session.getAttribute("username") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        String role = null;

        // Support both teacher credentials and admin credentials
        if ("hr".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "HR";
        } else if ("manager".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "MANAGER";
        } else if ("employee".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "EMPLOYEE";
        } else if ("admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
            role = "HR";
        }

        if (role == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        String token = jwtService.generateToken(username, role);

        session.setAttribute("jwtToken", token);
        session.setAttribute("username", username);
        session.setAttribute("role", role);

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // REST API Endpoint for programmatic JWT token retrieval
    @PostMapping("/api/auth/token")
    @ResponseBody
    public ResponseEntity<?> generateJwtToken(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String role = null;
        if ("hr".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "HR";
        } else if ("manager".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "MANAGER";
        } else if ("employee".equalsIgnoreCase(username) && "1234".equals(password)) {
            role = "EMPLOYEE";
        } else if ("admin".equalsIgnoreCase(username) && "admin123".equals(password)) {
            role = "HR";
        }

        if (role == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

        String token = jwtService.generateToken(username, role);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");
        response.put("username", username);
        response.put("role", role);
        return ResponseEntity.ok(response);
    }
}
