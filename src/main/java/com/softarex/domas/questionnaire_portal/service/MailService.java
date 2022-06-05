package com.softarex.domas.questionnaire_portal.service;


import com.softarex.domas.questionnaire_portal.entity.user.User;
import com.softarex.domas.questionnaire_portal.property.MailProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailProperty mailProperty;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailProperty mailProperty) {
        this.javaMailSender = javaMailSender;
        this.mailProperty = mailProperty;
    }

    public void sendMessage(User user, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperty.getFrom());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
