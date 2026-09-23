package com.example;

public class WhatsAppMessageService implements MessageService 
{
    @Override
    public String getMessage() 
    {
        return "[WhatsApp Service] Secure end-to-end encrypted notification sent!";
    }
}
