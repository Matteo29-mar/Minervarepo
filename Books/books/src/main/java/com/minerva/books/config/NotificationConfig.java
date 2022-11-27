package com.minerva.books.config;

import com.minerva.books.services.BookNotificationSender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public Queue sendNotification(){
        return new Queue("books-queue");
    }

    @Bean
    public BookNotificationSender sender(){
        return new BookNotificationSender();
    }

}
