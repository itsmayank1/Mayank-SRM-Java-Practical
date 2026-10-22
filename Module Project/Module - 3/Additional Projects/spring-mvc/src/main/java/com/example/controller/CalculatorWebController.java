package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorWebController {

    @GetMapping("/")
    public String showHome() {
        return "index";
    }

    @GetMapping("/calculator")
    public String showCalculator() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam("num1") double num1,
            @RequestParam("num2") double num2,
            @RequestParam("operation") String operation,
            Model model) {

        double result = 0.0;
        String symbol = "";
        String error = null;

        switch (operation.toUpperCase()) {
            case "ADD":
                result = num1 + num2;
                symbol = "+";
                break;
            case "SUBTRACT":
                result = num1 - num2;
                symbol = "-";
                break;
            case "MULTIPLY":
                result = num1 * num2;
                symbol = "\u00D7";
                break;
            case "DIVIDE":
                if (num2 != 0) {
                    result = num1 / num2;
                    symbol = "/";
                } else {
                    error = "Math Error: Cannot divide by zero.";
                }
                break;
            case "MODULO":
                if (num2 != 0) {
                    result = num1 % num2;
                    symbol = "MOD";
                } else {
                    error = "Math Error: Modulo by zero is undefined.";
                }
                break;
            case "POWER":
                result = Math.pow(num1, num2);
                symbol = "^";
                break;
            default:
                error = "Unsupported operation specified.";
        }

        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("symbol", symbol);
        model.addAttribute("operation", operation);
        model.addAttribute("result", result);
        model.addAttribute("error", error);

        return "calculator";
    }
}
