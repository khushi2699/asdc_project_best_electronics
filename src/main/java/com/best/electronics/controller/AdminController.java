package com.best.electronics.controller;

import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin", new Login());
        return "adminLogin";
    }

    @GetMapping("/process_login")
    public String processLogin(Login admin, Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new AdminLoginHandler();
        LoginState loginState = loginHandler.login(admin.getEmailAddress(), admin.getPassword(), request);
        model.addAttribute("msg", loginState.getLoginStatus());
        return loginState.getNextPage();
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);

        model.addAttribute("admin", new Login());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "userLogin";
    }
}
