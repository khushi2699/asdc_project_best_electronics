package com.best.electronics.controller;

import com.best.electronics.entity.User;
import com.best.electronics.service.UserPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    UserPersistenceService userPersistenceService;

    @PostMapping("/process_registration")
    public String processRegistration(User user){
        try{
            if(isUserAlreadyPresent(user.getEmailAddress())){
                return "registerFail";
            }else{
                userPersistenceService.saveUser(user);
                return "registerSuccess";
            }
        } catch (Exception e){
            return "registerFail";
        }

    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    public boolean isUserAlreadyPresent(String userEmail) throws Exception {
        return userPersistenceService.findUserByEmailAddress(userEmail) != null;
    }
}
