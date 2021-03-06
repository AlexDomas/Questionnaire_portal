package com.softarex.domas.questionnaire_portal.config;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Setter
@ConfigurationProperties(prefix = "app")
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    private String allowed;

    private int strength;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(allowed)
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }

}
