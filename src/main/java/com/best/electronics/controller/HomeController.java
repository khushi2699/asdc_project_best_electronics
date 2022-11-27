package com.best.electronics.controller;

import com.best.electronics.properties.DatabaseProperties;
import com.best.electronics.properties.PropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return "adminLogin";
    }
}

