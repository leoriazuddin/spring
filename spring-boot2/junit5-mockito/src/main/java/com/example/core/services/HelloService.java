package com.example.core.services;

import com.example.core.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    HelloRepository helloRepository;

    public String get() {
        return helloRepository.get();
    }
}
