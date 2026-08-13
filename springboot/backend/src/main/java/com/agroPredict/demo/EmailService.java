package com.agroPredict.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendReminder(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setText("please water the farmland" +
                "\nif raining in your region don't water the framland");
        mailSender.send(message);
    }

}
