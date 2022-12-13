package com.best.electronics.controller;

import com.best.electronics.email.ChangePasswordHandler;
import com.best.electronics.email.ResetPasswordCombinationValidationHandler;
import com.best.electronics.email.SendMailForForgotPassword;
import com.best.electronics.forgotPassword.ForgotPasswordState;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class ForgotPasswordController {

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        model.addAttribute("login", new User());
        return "forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model){
        model.addAttribute("login", new User());
        return "resetPassword";
    }

    @PostMapping("/enterNewPassword")
    public String enterNewPassword(@ModelAttribute User user, Model model) throws Exception {
        model.addAttribute("login", new User());
        ChangePasswordHandler changePasswordHandler = new ChangePasswordHandler();
        ForgotPasswordState forgotPasswordState = changePasswordHandler.storeNewPassword(user.getPassword(), user.getConfirmPassword(), user.getEmailAddress());
        if(forgotPasswordState.getStatus().equalsIgnoreCase("Done")){
            model.addAttribute("msg", "Password changed. Go to:  http://localhost:8080/user/login");
            return "redirect:/user/login";

        }else if (forgotPasswordState.getStatus().equalsIgnoreCase("Password and Confirm password do not match")) {
            model.addAttribute("msg", forgotPasswordState.getStatus());
            return "changePassword";
        }
        else if (forgotPasswordState.getStatus()
                .equalsIgnoreCase("Invalid password format")){
            model.addAttribute("msg", forgotPasswordState.getStatus());
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

    @PostMapping("/checkValidToken")
    public String checkValidToken(@ModelAttribute User user, Model model) {
        ResetPasswordCombinationValidationHandler resetPasswordCombinationValidationHandler = new ResetPasswordCombinationValidationHandler();
        model.addAttribute("login", new User());
        model.addAttribute("emailAddress", user.getEmailAddress());
        if(resetPasswordCombinationValidationHandler.checkCombination(user.getToken(), user.getEmailAddress())){
            return "changePassword";
        }
        else {
            model.addAttribute("msg","Please enter correct combination");
            return "resetPassword";

        }
    }
}
