package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.RegisterMailModel;
import com.bilgeadam.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProducer {

    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;

    @Value("${rabbitmq.key-register}")
    private String bindingKeyRegister;

    @Value("${rabbitmq.key-mail-sender}")
    private String bindingKeyMailSender;


    private final RabbitTemplate rabbitTemplate;

    public void sendNewUser(RegisterModel model){
        rabbitTemplate.convertAndSend(directExchange,bindingKeyRegister,model);
    }

    public void sendNewMail(RegisterMailModel model){
        rabbitTemplate.convertAndSend(directExchange,bindingKeyMailSender,model);
    }
}