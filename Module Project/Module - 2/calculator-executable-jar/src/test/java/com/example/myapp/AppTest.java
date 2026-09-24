package com.example.myapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest 
{
    @Test
    public void testBasicAddition() 
    {
        double a = 15.0;
        double b = 25.0;
        assertEquals(40.0, a + b, 0.0001);
    }

    @Test
    public void testDivision() 
    {
        double a = 50.0;
        double b = 5.0;
        assertEquals(10.0, a / b, 0.0001);
    }

    @Test
    public void testModulo() 
    {
        double a = 29.0;
        double b = 5.0;
        assertEquals(4.0, a % b, 0.0001);
    }

    @Test
    public void testPower() 
    {
        double base = 2.0;
        double exp = 5.0;
        assertEquals(32.0, Math.pow(base, exp), 0.0001);
    }

    @Test
    public void testSquareRoot() 
    {
        double val = 64.0;
        assertEquals(8.0, Math.sqrt(val), 0.0001);
    }

    @Test
    public void testPercentage() 
    {
        double total = 200.0;
        double percent = 15.0;
        assertEquals(30.0, (total * percent) / 100.0, 0.0001);
    }
}
