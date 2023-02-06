package com.m2u.pushnotif.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/welcome")
public class CallbackController {

    @GetMapping
    public String welcome() {
        return "M2U Push Notif ";
    }

    @PostMapping
    public String other() {
        return "OK";
    }

}
