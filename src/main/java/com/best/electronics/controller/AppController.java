package com.best.electronics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/welcome")
    public String viewHomePage() {
        return "index";
    }
}
