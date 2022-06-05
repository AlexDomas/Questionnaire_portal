package com.softarex.domas.questionnaire_portal.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class MailProperty {
    @Value("${mailService.from}") private String from;
    @Value("${mailService.registration.text}") private String registrationText;
    @Value("${mailService.registration.subject}") private String registrationSubject;
    @Value("${mailService.password.text}") private String passwordChangeText;
    @Value("${mailService.password.subject}") private String passwordChangeSubject;

    public String getFrom() {
        return from;
    }

    public String getRegistrationText() {
        return registrationText;
    }

    public String getRegistrationSubject() {
        return registrationSubject;
    }

    public String getPasswordChangeText() {
        return passwordChangeText;
    }

    public String getPasswordChangeSubject() {
        return passwordChangeSubject;
    }
}
