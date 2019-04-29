package com.example.core;

import com.example.core.repository.HelloRepository;
import com.example.core.services.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public HelloService helloService() {
        return new HelloService();
    }

    @Bean
    public HelloRepository helloRepository() {
        return new HelloRepository();
    }
}
