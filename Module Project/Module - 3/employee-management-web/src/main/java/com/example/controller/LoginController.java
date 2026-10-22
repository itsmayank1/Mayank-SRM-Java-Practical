package com.example.controller;

import com.example.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public String rootRedirect() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model,
                            Authentication authentication) {
        // If already logged in, redirect directly to dashboard
        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/dashboard";
        }

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login";
    }

    // JWT Auth Endpoint - for REST API clients / curl / Postman
    @PostMapping("/api/auth/token")
    @ResponseBody
    public ResponseEntity<?> generateJwtToken(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        String role = "ROLE_USER";
        if ("admin".equals(username) && "admin123".equals(password)) {
            role = "ROLE_ADMIN";
        } else if ("manager".equals(username) && "manager123".equals(password)) {
            role = "ROLE_MANAGER";
        } else if ("employee".equals(username) && "employee123".equals(password)) {
            role = "ROLE_USER";
        } else {
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
