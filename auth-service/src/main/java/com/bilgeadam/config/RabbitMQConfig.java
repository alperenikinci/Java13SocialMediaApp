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
    private String bindingKeyRegister; //Register queue'su icin binding islemlerimi yaparken kullanacagim anahtar.

    @Value("${rabbitmq.queue-register}")
    private String queueRegister; //Register islemleri icin olusturacagim queue.

    @Value("${rabbitmq.queue-mail-sender}")
    private String queueMailSender; //Register sirasinda kullaniciya gonderecegim mail queue.
    @Value("${rabbitmq.key-mail-sender}")
    private String bindingKeyMailSender; //MailSender queue'su icin binding islemlerimi yaparken kullanacagim anahtar.

    @Bean
    public DirectExchange exchangeAuth(){ //Sabittir, 1 tanedir, bulundugu servisin degerini alir.
        return new DirectExchange(exchange);//auth-exchange
    }
    @Bean
    public Queue queueRegister(){
        return new Queue(queueRegister);
    }
    @Bean
    public Binding bindingRegister(final Queue queueRegister, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(queueRegister).to(exchangeAuth).with(bindingKeyRegister);
    }

    @Bean
    public Queue queueMailSender(){
        return new Queue(queueMailSender);
    }
    @Bean
    public Binding bindingMailSender(final Queue queueMailSender, final DirectExchange exchangeAuth){
        return BindingBuilder.bind(queueMailSender).to(exchangeAuth).with(bindingKeyMailSender);
    }



}