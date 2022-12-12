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
        LoginState loginState = loginHandler.login(admin, request);
        model.addAttribute("msg", loginState.getLoginStatus());
        model.addAttribute("admin", new Admin());
        return loginState.getNextPage();
    }

    @GetMapping("/adminHome")
    public String adminHome(Admin admin, Model model, HttpServletRequest request){
        return "adminLandingPage";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);
        model.addAttribute("admin", new Admin());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "adminLogout";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Admin admin, Model model, HttpServletRequest request){
        Admin admin1 = new Admin();
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Order> orderDetails = admin1.getOrderDetails(databasePersistence);
        System.out.println(orderDetails);
        model.addAttribute("orders", orderDetails);
        return "adminOrderList";
    }
    @GetMapping("/users")
    public String adminUsers(Model model) throws Exception{
        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();

        ArrayList<Map<String, Object>> userList = productPersistence.getAllUsersDetails(db);
        if(userList == null){
            throw new NullPointerException("Users List could not be fetched from the database");
        }
        else {
            model.addAttribute("user", new User());
            model.addAttribute("listUser", userList);
            return "adminUsersList";
        }
    }

    @GetMapping("/products")
    public String adminProducts(Model model) throws Exception{
        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();

        ArrayList<Map<String, Object>> productList = productPersistence.getDetails(db);
        if(productList == null){
            throw new NullPointerException("Product List could not be fetched from the database");
        }
        else {
            model.addAttribute("product", new Product());
            model.addAttribute("listProducts", productList);
            return "adminProductList";
        }
    }

    @GetMapping("/profile")
    public String adminProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
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

    @GetMapping("/editProfile")
    public String adminEditProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
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

    @PostMapping("/update_profile")
    public String processUpdateProfile(Admin admin, HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            String message = admin.updateAdminDetails(databasePersistence);
            oldSession.setAttribute("updatedStatus", message);
            return "redirect:/admin/editProfile";
        }
        return "AdminLogin";
    }

}
