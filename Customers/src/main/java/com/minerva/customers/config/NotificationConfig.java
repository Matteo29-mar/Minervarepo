package com.minerva.customers.config;

import com.minerva.customers.services.CustomerNotificationSender;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public Queue sendNotification(){
        return new Queue("customer-queue");
    }

    @Bean
    public CustomerNotificationSender sender(){
        return new CustomerNotificationSender();
    }

}