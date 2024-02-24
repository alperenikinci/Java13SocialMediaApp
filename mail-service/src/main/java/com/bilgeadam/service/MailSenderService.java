package com.bilgeadam.service;

import com.bilgeadam.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;


//    @EventListener(ApplicationReadyEvent.class) Uygulama ayaga kalktiginda metodun bir kerelik calistirilmasi icin. (deneme asamasinda kullandik)
    public void sendMail(RegisterMailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ai.boostjava11@gmail.com");
        mailMessage.setTo("ai.boostjava11@gmail.com"); // burasi development tamamlandiginda kullanici mailiyle degistirilecek.
//        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("AKTIVASYON KODUNUZ...");
        mailMessage.setText(model.getUsername()+" Aramıza hoşgeldin!\n"+"Hesabınızı doğrulamak için aktivasyon kodunuz: "+model.getActivationCode());
        javaMailSender.send(mailMessage);
    }


}
