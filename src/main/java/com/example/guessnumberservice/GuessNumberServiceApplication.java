package com.example.guessnumberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GuessNumberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuessNumberServiceApplication.class, args);
    }

}
