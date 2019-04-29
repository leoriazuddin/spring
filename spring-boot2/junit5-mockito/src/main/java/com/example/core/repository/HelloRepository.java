package com.example.core.repository;

import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {

    public String get() {
        return "Junit 5, Hello";
    }
}
