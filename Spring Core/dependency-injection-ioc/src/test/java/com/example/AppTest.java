package com.example;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class AppTest 
{
    @Test
    public void testSpringBeansConfiguration() 
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        MessagePrinter setterPrinter = context.getBean("messagePrinterSetter", MessagePrinter.class);
        assertNotNull(setterPrinter);

        MessagePrinter constructorPrinter = context.getBean("messagePrinterConstructor", MessagePrinter.class);
        assertNotNull(constructorPrinter);

        // Verify Singleton scope
        MessagePrinter s1 = context.getBean("messagePrinterSetter", MessagePrinter.class);
        MessagePrinter s2 = context.getBean("messagePrinterSetter", MessagePrinter.class);
        assertSame(s1, s2);

        // Verify Prototype scope
        MessagePrinter p1 = context.getBean("messagePrinterPrototype", MessagePrinter.class);
        MessagePrinter p2 = context.getBean("messagePrinterPrototype", MessagePrinter.class);
        assertNotSame(p1, p2);

        context.close();
    }
}
