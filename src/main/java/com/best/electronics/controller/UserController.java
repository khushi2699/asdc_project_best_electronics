package com.best.electronics.controller;

import com.best.electronics.database.ILoginHandler;
import com.best.electronics.database.UserLoginHandler;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/process_registration")
    public String processRegistration(User user){
        return "registerSuccess";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @PostMapping("/process_login")
    public String processLogin(User user, HttpServletRequest request) {
        ILoginHandler loginHandler = new UserLoginHandler();
        if(loginHandler.login(user.getEmailAddress(), user.getPassword(), request)){
            return "productList";
        }
        return "products";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "userLogin";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model){
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);
        return "userLogin";
    }
}
