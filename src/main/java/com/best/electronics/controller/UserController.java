package com.best.electronics.controller;

import com.best.electronics.controller.email.ResetPasswordCombinationValidationHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Login;
import com.best.electronics.model.User;
import com.best.electronics.register.RegisterHandler;
import com.best.electronics.register.RegisterState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/process_registration")
    public String processRegistration(User user, Model model){
        RegisterHandler registerHandler = new RegisterHandler();
        RegisterState registerState = registerHandler.register(user);
        model.addAttribute("msg", registerState.getRegisterStatus());
        return registerState.getNextPage();
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @PostMapping("/process_login")
    public String processLogin(Login user, Model model, HttpServletRequest request) {
        ILoginHandler loginHandler = new UserLoginHandler();
        LoginState loginState = loginHandler.login(user.getEmailAddress(), user.getPassword(), request);
        model.addAttribute("msg", loginState.getLoginStatus());
        model.addAttribute("user", new Login());
        return loginState.getNextPage();
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new Login());
        return "userLogin";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model){
        model.addAttribute("login", new Login());
        System.out.println("Printing reset Password");
        return "resetPassword";
    }

    @PostMapping("/checkValidToken")
    public String checkValidToken(@ModelAttribute Login login, Model model) {
        ResetPasswordCombinationValidationHandler resetPasswordCombinationValidationHandler = new ResetPasswordCombinationValidationHandler();
        System.out.println("Email address" + login.getEmailAddress());
        System.out.println("Token" + login.getToken());
        model.addAttribute("login", new Login());
        System.out.println(resetPasswordCombinationValidationHandler.checkCombination(login.getToken(), login.getEmailAddress()));
        model.addAttribute("msg",resetPasswordCombinationValidationHandler.checkCombination(login.getToken(), login.getEmailAddress()));
        return "resetPassword";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);

        model.addAttribute("user", new Login());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "userLogin";
    }
}
