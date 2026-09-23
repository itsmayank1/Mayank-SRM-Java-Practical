package com.example.myapp;

public class App 
{
    public static String getGreeting() 
    {
        return "Hello World from SRM Java Full Stack Maven Application!";
    }

    public static void main(String[] args) 
    {
        System.out.println("==================================================");
        System.out.println("   " + getGreeting());
        System.out.println("   Java Version : " + System.getProperty("java.version"));
        System.out.println("   Operating OS : " + System.getProperty("os.name"));
        System.out.println("==================================================");
    }
}
