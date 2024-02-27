package com.bilgeadam.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange-user}")
    private String exchange;

    @Value("${rabbitmq.queue-register}")
    private String queueRegister;

    @Value("${rabbitmq.key-elastic-register}")
    private String bindingKeyElasticRegister;

    @Value("${rabbitmq.queue-elastic-register}")
    private String queueElasticRegister;

    @Bean
    public DirectExchange exchangeUser(){
        return new DirectExchange(exchange);
    }
    @Bean
    public Queue queueRegister(){
        return new Queue(queueRegister);
    }

    @Bean Queue queueElasticRegister(){
        return new Queue(queueElasticRegister);
    }
    @Bean
    public Binding bindingElasticRegister(final Queue queueElasticRegister, final DirectExchange exchangeUser){
        return BindingBuilder.bind(queueElasticRegister).to(exchangeUser).with(bindingKeyElasticRegister);

    }
}