package com.minerva.notificationservice.services;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements MessageListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void onMessage(Message message) {
        System.out.println("New Message: " + new String(message.getBody()));
    }

}