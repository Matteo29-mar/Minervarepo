package com.minerva.notificationservice.services;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class NotificationService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("New Message: " + new String(message.getBody()));
    }

}
