package com.example;

public class MessagePrinter 
{
    private MessageService service;
    private String clientName = "Default Client";

    // Default constructor for Setter Injection
    public MessagePrinter() 
    {
    }

    // Parametric constructor for Constructor Injection
    public MessagePrinter(MessageService service, String clientName) 
    {
        this.service = service;
        this.clientName = clientName;
    }

    // Setter Injection
    public void setService(MessageService service) 
    {
        this.service = service;
    }

    public void setClientName(String clientName) 
    {
        this.clientName = clientName;
    }

    public void printMessage() 
    {
        System.out.printf("[%s] -> %s%n", clientName, service != null ? service.getMessage() : "No Service Bound");
    }

    // Lifecycle Callback: Initialization
    public void init() 
    {
        System.out.println("  [Spring Bean Lifecycle] MessagePrinter bean initialized.");
    }

    // Lifecycle Callback: Destruction
    public void cleanup() 
    {
        System.out.println("  [Spring Bean Lifecycle] MessagePrinter bean destroyed / released.");
    }
}