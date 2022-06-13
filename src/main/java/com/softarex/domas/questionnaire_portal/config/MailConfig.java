package com.softarex.domas.questionnaire_portal.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@ConfigurationProperties(prefix = "mail")
@Configuration
public class MailConfig {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String protocol;

    private Boolean debug;

}
