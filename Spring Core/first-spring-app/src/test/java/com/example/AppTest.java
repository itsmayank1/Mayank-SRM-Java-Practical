package com.example;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.Assert.*;

public class AppTest 
{
    @Test
    public void testAnnotationContext() 
    {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        
        MyService myService = ctx.getBean(MyService.class);
        assertNotNull(myService);

        OrderService orderService = ctx.getBean(OrderService.class);
        assertNotNull(orderService);

        PaymentGateway paymentGateway = ctx.getBean(PaymentGateway.class);
        assertNotNull(paymentGateway);

        ctx.close();
    }
}
