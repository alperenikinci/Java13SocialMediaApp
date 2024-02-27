package com.bilgeadam.rabbitmq.consumer;


import com.bilgeadam.rabbitmq.model.RegisterElasticModel;
import com.bilgeadam.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterElasticConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.queue-elastic-register}") //register-queue
    public void createNewUser(RegisterElasticModel model){
        log.info("User {}",model.toString());
        userProfileService.createUserWithRabbitMq(model);
    }



}