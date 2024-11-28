package com.subhajit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return  "Welcome to Crypto treading platform";
    }

    @GetMapping("/api")
    public String secure() {
        return  "Welcome to Crypto treading platform Secure";
    }
}
