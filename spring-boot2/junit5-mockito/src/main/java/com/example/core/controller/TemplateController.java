package com.example.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLogin() {
        return "login_form";
    }

    @GetMapping("courses")
    public String getCourses() {
        return "courses";
    }

    @GetMapping("logout")
    public String logout() {
        return "logout";
    }
}
