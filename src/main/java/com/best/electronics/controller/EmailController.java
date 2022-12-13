package com.best.electronics.controller;

import com.best.electronics.email.ChangePasswordHandler;
import com.best.electronics.email.SendMailForForgotPassword;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class EmailController {

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        model.addAttribute("login", new User());
        return "forgotPassword";
    }

    @PostMapping("/enterNewPassword")
    public String enterNewPassword(@ModelAttribute User user, Model model) throws Exception {
        model.addAttribute("login", new User());
        ChangePasswordHandler changePasswordHandler = new ChangePasswordHandler();
        String status = changePasswordHandler.storeNewPassword(user.getPassword(), user.getConfirmPassword(), user.getEmailAddress());
        if(status.equalsIgnoreCase("Password changed")){
            model.addAttribute("msg", "Password changed. Click here to login! http://localhost:8080/user/login");
            return "changePassword";

        }else if (status.equalsIgnoreCase("No match")) {
            model.addAttribute("msg", "Password and Confirm Password does not match");
            return "changePassword";
        }
        else if (status.equalsIgnoreCase("Invalid pattern")){
            model.addAttribute("msg", "Invalid password pattern");
            return "changePassword";
        }
        else {
            return null;
        }
    }

    @PostMapping("/getCode")
    public String getCode(@ModelAttribute User user, Model model) throws Exception {
        System.out.println("Email address" + user.getEmailAddress());
        SendMailForForgotPassword sendMailForForgotPassword = new SendMailForForgotPassword();
        sendMailForForgotPassword.emailControl(user.getEmailAddress());
        model.addAttribute("login", new User());
        model.addAttribute("msg", "Password reset link and token will be sent to you email if the email exists!");
        return "forgotPassword";
    }
}
