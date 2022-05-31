package com.softarex.domas.questionnaire_portal.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:fields");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/answer_success").setViewName("answer_success");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
