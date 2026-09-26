package com.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main(String[] args) 
    {
        System.out.println("==========================================================");
        System.out.println("      SPRING CORE: INVERSION OF CONTROL & DI DEMO");
        System.out.println("            SRM Java Full Stack Practical");
        System.out.println("==========================================================\n");

        // 1. Initialize Spring IoC Application Context container
        System.out.println("[+] Bootstrapping Spring ClassPathXmlApplicationContext...");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("[+] Container initialized successfully.\n");

        // 2. Setter Injection Demonstration
        System.out.println("--- 1. SETTER INJECTION DEMONSTRATION ---");
        MessagePrinter setterPrinter = context.getBean("messagePrinterSetter", MessagePrinter.class);
        setterPrinter.printMessage();

        // 3. Constructor Injection Demonstration
        System.out.println("\n--- 2. CONSTRUCTOR INJECTION DEMONSTRATION ---");
        MessagePrinter constructorPrinter = context.getBean("messagePrinterConstructor", MessagePrinter.class);
        constructorPrinter.printMessage();

        // 4. Bean Scope Demonstration: Singleton vs Prototype
        System.out.println("\n--- 3. BEAN SCOPES DEMONSTRATION ---");
        MessagePrinter singleton1 = context.getBean("messagePrinterSetter", MessagePrinter.class);
        MessagePrinter singleton2 = context.getBean("messagePrinterSetter", MessagePrinter.class);
        System.out.println("Singleton instances identical? " + (singleton1 == singleton2) + " (HashCode: " + System.identityHashCode(singleton1) + ")");

        MessagePrinter proto1 = context.getBean("messagePrinterPrototype", MessagePrinter.class);
        MessagePrinter proto2 = context.getBean("messagePrinterPrototype", MessagePrinter.class);
        System.out.println("Prototype instances identical? " + (proto1 == proto2) + " (Hashes: " + System.identityHashCode(proto1) + " vs " + System.identityHashCode(proto2) + ")");
        proto1.printMessage();

        // 5. Gracefully close container to trigger destroy callbacks
        System.out.println("\n--- 4. SPRING CONTAINER SHUTDOWN ---");
        context.close();
        System.out.println("[+] Spring context closed successfully.");
    }
}