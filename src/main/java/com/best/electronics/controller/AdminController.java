package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Admin;
import com.best.electronics.model.Order;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;
import exceptions.NullPointerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        model.addAttribute("admin", new Admin());
        return "adminLogin";
    }

    @PostMapping("/process_login")
    public String processLogin(Admin admin, Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new AdminLoginHandler();
        LoginState loginState = loginHandler.login(admin.getEmailAddress(), admin.getPassword(), request);
        model.addAttribute("msg", loginState.getLoginStatus());
        model.addAttribute("admin", new Admin());
        return loginState.getNextPage();
    }

    @GetMapping("/adminLogout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);
        model.addAttribute("admin", new Admin());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "adminLogin";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Admin admin, Model model, HttpServletRequest request){

        Admin admin1 = new Admin();
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Order> orderDetails = admin1.getOrderDetails(databasePersistence);
        System.out.println(orderDetails);
        model.addAttribute("orders", orderDetails);
        return "orderList";
    }
    @GetMapping("/adminUsers")
    public String adminUsers(Model model) throws Exception{

        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();

        ArrayList<Map<String, Object>> userList = null;
        userList = productPersistence.getAllUsersDetails(db);
        Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

        if(userList == null){
            throw new NullPointerException("Users List could not be fetched from the database");
        }
        else {
            model.addAttribute("user", new User());
            model.addAttribute("listUser", userList);
            return "adminUsersList";
        }
    }

    @GetMapping("/adminProducts")
    public String adminProducts(Model model) throws Exception{

        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();

        ArrayList<Map<String, Object>> productList = null;
        productList = productPersistence.getDetails(db);
        Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

        if(productList == null){
            throw new NullPointerException("Product List could not be fetched from the database");
        }
        else {
            model.addAttribute("product", new Product());
            model.addAttribute("listProducts", productList);
            return "adminProductList";
        }
    }

    @GetMapping("/adminProfile")
    public String adminProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("adminId");
            Admin admin = new Admin();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> adminDetail = admin.getAdminDetails(id, databasePersistence);
            if(adminDetail != null){
                model.addAttribute("firstName", adminDetail.get("firstName"));
                model.addAttribute("lastName", adminDetail.get("lastName"));
                model.addAttribute("emailAddress", adminDetail.get("emailAddress"));
            }
            return "adminProfile";
        }
        return "adminLogin";
    }

    @GetMapping("/adminEditProfile")
    public String adminEditProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("adminId");
            String updatedStatus = (String) oldSession.getAttribute("updatedStatus");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("updatedStatus");
            }

            Admin admin = new Admin();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> adminDetail = admin.getAdminDetails(id, databasePersistence);
            if(adminDetail == null){
                model.addAttribute("updatedStatus", "Some exception occurred! Please try again!");
            }

            model.addAttribute("firstName", adminDetail.get("firstName"));
            model.addAttribute("lastName", adminDetail.get("lastName"));
            model.addAttribute("email", adminDetail.get("emailAddress"));
            model.addAttribute("updatedStatus", updatedStatus);
            return "editAdminDetails";
        }
        return "adminLogin";
    }

}
