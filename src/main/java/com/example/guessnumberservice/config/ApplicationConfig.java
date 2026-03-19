//package com.example.guessnumberservice.config;
//
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApplicationConfig {
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        messageSource.setDefaultEncoding("UTF-8");
//
//        // 🔥 QUAN TRỌNG
//        messageSource.setUseCodeAsDefaultMessage(true);
//
//        return messageSource;
//    }
//}
