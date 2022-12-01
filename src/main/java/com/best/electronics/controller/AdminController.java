package com.best.electronics.controller;

import com.best.electronics.database.AdminLoginHandler;
import com.best.electronics.database.ILoginHandler;
import com.best.electronics.database.UserLoginHandler;
import com.best.electronics.model.Admin;
import com.best.electronics.model.User;
import com.best.electronics.session.SessionManager;
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
        model.addAttribute("admin", new Admin());
        return "adminLogin";
    }

    @GetMapping("/process_login")
    public String processLogin(Admin admin, HttpServletRequest request){
        ILoginHandler loginHandler = new AdminLoginHandler();

        if(loginHandler.login(admin.getEmailAddress(), admin.getPassword(), request)){
            SessionManager sessionManager = new SessionManager();
            sessionManager.getSession(request);

            return "";
        }
        return "";
    }
}
