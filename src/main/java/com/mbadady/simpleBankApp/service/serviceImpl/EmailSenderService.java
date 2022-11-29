package com.mbadady.simpleBankApp.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void emailSender(String toEmail,
                            String subject,
                            String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setText(body);
        message.setFrom("victorsomtochukwu@gmail.com");
        message.setSubject(subject);

        mailSender.send(message);
    }
}
