package com.minerva.borrowing.service;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;




public class BorrowingNotificationSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    public BorrowingNotificationSender() {

    }
    public void SendNotification(String message){
        this.template.convertAndSend(queue.getName(), message);
    }









}


