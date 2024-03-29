package com.bilgeadam.config;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue-mail-sender}")
    private String queueMailSender;

    @Bean
    public Queue queueRegister(){
        return new Queue(queueMailSender);
    }

}