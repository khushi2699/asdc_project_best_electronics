package com.best.electronics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/welcome")
    public String viewWelcomePage() {
        return "welcome";
    }

    @GetMapping("/user")
    public String viewUserIndexPage() {
        return "userIndex";
    }

    @GetMapping("/admin")
    public String viewAdminIndexPage() {
        return "adminIndex";
    }

}
