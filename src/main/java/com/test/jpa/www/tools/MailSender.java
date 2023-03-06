package com.test.jpa.www.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String emailTo, String subjects, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(this.username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subjects);
        mailMessage.setText(message);

        this.mailSender.send(mailMessage);
    }
}
