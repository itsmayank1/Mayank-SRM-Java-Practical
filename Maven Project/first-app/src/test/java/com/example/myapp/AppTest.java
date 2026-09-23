package com.example.myapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest 
{
    @Test
    public void testGreetingNotNull() 
    {
        String greeting = App.getGreeting();
        assertNotNull(greeting);
        assertTrue(greeting.contains("Hello World"));
    }
}
