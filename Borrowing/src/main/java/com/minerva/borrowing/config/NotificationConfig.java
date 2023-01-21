package com.minerva.borrowing.config;


import com.minerva.borrowing.service.BorrowingNotificationSender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    @Bean
    public Queue sendNotification(){
        return new Queue("borrowing-queue");
    }
    @Bean
    public BorrowingNotificationSender sender(){
        return new BorrowingNotificationSender();
    }
}
