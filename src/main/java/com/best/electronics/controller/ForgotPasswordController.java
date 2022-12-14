package com.best.electronics.controller;

import com.best.electronics.email.ChangePasswordHandler;
import com.best.electronics.email.ResetPasswordCombinationValidationHandler;
import com.best.electronics.forgotPassword.ForgotPasswordState;
import com.best.electronics.forgotPassword.GetCode;
import com.best.electronics.forgotPassword.ResetPasswordFactory;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        model.addAttribute("msg", forgotPasswordState.getStatus());
        return forgotPasswordState.getNextPage();
    }
    @PostMapping("/getCode")
    public String getCode(@ModelAttribute User user, Model model){
        //implementing an open approach to send codes through either email or text message. But implementation is of email
        ResetPasswordFactory resetPasswordFactory = new ResetPasswordFactory();
        GetCode getCode = resetPasswordFactory.sendCodeThrough("Email");
        getCode.generateCode(user.getEmailAddress());

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
