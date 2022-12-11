package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Adminn;
import com.best.electronics.model.Login;
import com.best.electronics.model.Order;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin", new Login());
        return "adminLogin";
    }

    @PostMapping("/process_login")
    public String processLogin(Login admin, Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new AdminLoginHandler();
        LoginState loginState = loginHandler.login(admin.getEmailAddress(), admin.getPassword(), request);
        model.addAttribute("msg", loginState.getLoginStatus());
        model.addAttribute("admin", new Login());
        return loginState.getNextPage();
//        return "adminProfile";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);

        model.addAttribute("admin", new Login());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "userLogin";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Login admin, Model model, HttpServletRequest request){

        Adminn admin1 = new Adminn();
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Order> orderDetails = admin1.getOrderDetails(databasePersistence);
        System.out.println(orderDetails);
        System.out.println(orderDetails);
        System.out.println(orderDetails);
        model.addAttribute("orders", orderDetails);
        return "orderList";
    }

}
