package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.processing.Generated;

@Service
class MyService 
{
    public void hello() 
    {
        System.out.println("  [MyService] Hello from Spring Context Annotation Configuration!");
    }
}

@Component
class PaymentGateway 
{
    public boolean processPayment(double amount, String method) 
    {
        System.out.printf("  [PaymentGateway] Successfully authorized \u20B9%.2f using [%s]%n", amount, method);
        return true;
    }
}

@Service
class OrderService 
{
    private final PaymentGateway paymentGateway;
    private final MyService myService;

    // Autowired Constructor Injection
    @Autowired
    public OrderService(PaymentGateway paymentGateway, MyService myService) 
    {
        this.paymentGateway = paymentGateway;
        this.myService = myService;
    }

    public void placeOrder(String orderId, double totalAmount) 
    {
        System.out.println("  [OrderService] Processing incoming order: " + orderId);
        myService.hello();
        paymentGateway.processPayment(totalAmount, "UPI / NetBanking");
        System.out.println("  [OrderService] Order " + orderId + " completed and recorded successfully.");
    }
}

@Configuration
@ComponentScan(basePackages = "com.example")
public class App 
{
    public static void main(String[] args) 
    {
        System.out.println("==========================================================");
        System.out.println("     SPRING CORE: ANNOTATION-BASED APPLICATION");
        System.out.println("         SRM Java Full Stack Practical");
        System.out.println("==========================================================\n");

        System.out.println("[+] Scanning package 'com.example' with AnnotationConfigApplicationContext...");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        System.out.println("[+] Spring application context bootstrapped.\n");

        // 1. Basic Service Lookup
        System.out.println("--- 1. BASIC COMPONENT LOOKUP ---");
        MyService service = ctx.getBean(MyService.class);
        service.hello();

        // 2. Autowired Layered Service Injection
        System.out.println("\n--- 2. AUTOWIRED DEPENDENCY INJECTION DEMO ---");
        OrderService orderService = ctx.getBean(OrderService.class);
        orderService.placeOrder("SRM-ORD-2026-9001", 3450.00);

        // 3. Gracefully close Spring context
        System.out.println("\n--- 3. CONTAINER SHUTDOWN ---");
        ctx.close();
        System.out.println("[+] Context closed cleanly.");
    }
}
