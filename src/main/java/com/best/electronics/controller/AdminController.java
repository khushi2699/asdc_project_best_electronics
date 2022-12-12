package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Admin;
import com.best.electronics.model.Login;
import com.best.electronics.model.Order;
import com.best.electronics.model.Product;
import com.best.electronics.sendEmail.SendOrderStatusEmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);

        model.addAttribute("admin", new Login());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "adminLogin";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Order order, Admin admin, Model model, HttpServletRequest request){

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) {
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ArrayList<Order> orderDetails = admin.getOrderDetails(databasePersistence);

            System.out.println(orderDetails.toString());
            model.addAttribute("orders", orderDetails);
            model.addAttribute("order", order);
            String updatedStatus = (String) oldSession.getAttribute("msg");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("msg");
            }
            model.addAttribute("msg",updatedStatus);
            return "orderList";
        }
        return "adminLogin";
    }

    @PostMapping("/sendEmail")
    public String processUpdateProfile(
       @RequestParam(value = "orderId", required = false) Integer orderId,
       @RequestParam(value = "orderAmount", required = false) Double orderAmount,
       @RequestParam(value = "orderStatus", required = false) String orderStatus,
       @RequestParam(value = "orderDate", required = false) String orderDate,
       @RequestParam(value = "emailAddress", required = false) String emailAddress,
       HttpServletRequest request) throws MessagingException {

        Admin admin = new Admin();
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) {
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ArrayList<Order> orderDetails = admin.getOrderDetails(databasePersistence);

            for (Order order1 : orderDetails) {
                ArrayList<Product> products = order1.getProducts();
                SendOrderStatusEmail email = new SendOrderStatusEmail();
                if (email.sendEmail(orderId, orderAmount, orderDate, emailAddress, orderStatus, products)) {
                    oldSession.setAttribute("msg", "Email is successfully sent!");
                    return "redirect:/admin/orderDetails";

                } else {
                    oldSession.setAttribute("msg", "Some error occurred while sending email! Please try again!");
                    return "redirect:/admin/orderDetails";
                }
            }
        }
        return "adminLogin";

    }
}
