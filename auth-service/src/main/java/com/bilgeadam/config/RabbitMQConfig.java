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


    @Value("${rabbitmq.exchange-auth}")
    private String exchange; // Sabittir, 1 tanedir, bulunduğu servisin degerini tasir.

    @Value("${rabbitmq.key-register}")
    private String registerBindingKey; //Register queue'su icin binding islemlerimi yaparken kullanacagim anahtar.

    @Value("${rabbitmq.queue-register}")
    private String queueRegister; //Register islemleri icin olusturacagim queue.


    @Bean
    public DirectExchange exchangeAuth(){ //Sabittir, 1 tanedir, bulundugu servisin degerini alir.
        return new DirectExchange(exchange);//auth-exchange
    }
    @Bean
    public Queue queueRegister(){
        return new Queue(queueRegister);
    }
    @Bean
    public Binding bindingRegister(final Queue registerQueue, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }


}