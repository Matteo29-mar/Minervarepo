package com.minerva.books.services;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BookNotificationSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;
    public BookNotificationSender() {
    }

    public void SendNotification(String message){
        this.template.convertAndSend(queue.getName(), message);
        System.out.println("Message sent with content: " + message);
    }



}
