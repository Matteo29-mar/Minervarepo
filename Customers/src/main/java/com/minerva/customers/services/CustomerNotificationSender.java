package com.minerva.customers.services;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
public class CustomerNotificationSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    public CustomerNotificationSender() {
    }

    public void SendNotification(String message){
        this.template.convertAndSend(queue.getName(), message);
    }
}
