package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.*;
import com.best.electronics.exceptions.NullPointerException;
import com.best.electronics.repository.AdminRepository;
import com.best.electronics.repository.ProductRepository;
import com.best.electronics.repository.UserRepository;
import com.best.electronics.email.ISendOrderStatusEmail;
import com.best.electronics.email.SendOrderStatusEmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String adminHome(){
        return "adminLandingPage";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);
        model.addAttribute("admin", new Admin());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "adminLogin";
    }

    @GetMapping("/orderDetails")
    public String orderDetails(Order order, Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            String updatedStatus = (String) oldSession.getAttribute("msg");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("msg");
            }
            model.addAttribute("msg",updatedStatus);

            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            ArrayList<Order> orderDetails = adminRepository.getOrderDetails();
            model.addAttribute("orders", orderDetails);
            model.addAttribute("order", order);
            return "orderList";
        }
    }

    @GetMapping("/users")
    public String adminUsers(Model model) throws Exception{
        IDatabasePersistence db = new MySQLDatabasePersistence();
        UserRepository userRepository = new UserRepository(db);
        ArrayList<Map<String, Object>> userList = userRepository.getAllUsersDetails();
        if(userList == null){
            throw new NullPointerException("Users List could not be fetched from the database");
        } else {
            model.addAttribute("user", new User());
            model.addAttribute("listUser", userList);
            return "adminUsersList";
        }
    }

    @GetMapping("/products")
    public String adminProducts(Model model) throws Exception{
        IDatabasePersistence db = new MySQLDatabasePersistence();
        ProductRepository productRepository = new ProductRepository(db);
        ArrayList<Map<String, Object>> productCategoryList = productRepository.getAllProductsAndTheirCategory();
//        ArrayList<Map<String, Object>> productList = productRepository.getProductDetails();
        if(productCategoryList.isEmpty()){
            throw new NullPointerException("Products List could not be fetched from the database");
        }else {

//            model.addAttribute("productcategory", new ProductCategory());
            model.addAttribute("listProductCategory", productCategoryList);
//            model.addAttribute("product", new Product());
//            model.addAttribute("listProducts", productList);
            return "adminCategoryProduct";
        }
    }

    @GetMapping("/profile")
    public String adminProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            Map<String, Object> adminDetail = adminRepository.getAdminDetails(id);
            if(adminDetail != null){
                model.addAttribute("firstName", adminDetail.get("firstName"));
                model.addAttribute("lastName", adminDetail.get("lastName"));
                model.addAttribute("emailAddress", adminDetail.get("emailAddress"));
            }
            return "adminProfile";
        }
    }

    @GetMapping("/editProfile")
    public String adminEditProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "adminLogin";
        }else{
            Integer id = (Integer) oldSession.getAttribute("id");
            String updatedStatus = (String) oldSession.getAttribute("updatedStatus");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("updatedStatus");
            }

            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            Map<String, Object> adminDetail = adminRepository.getAdminDetails(id);
            if(adminDetail == null){
                model.addAttribute("updatedStatus", "Some exception occurred! Please try again!");
            }

            model.addAttribute("firstName", adminDetail.get("firstName"));
            model.addAttribute("lastName", adminDetail.get("lastName"));
            model.addAttribute("email", adminDetail.get("emailAddress"));
            model.addAttribute("updatedStatus", updatedStatus);
            return "editAdminDetails";
        }
    }

    @PostMapping("/update_profile")
    public String processUpdateProfile(Admin admin, HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "AdminLogin";
        }else{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            String message = adminRepository.updateAdminDetails(admin);
            oldSession.setAttribute("updatedStatus", message);
            return "redirect:/admin/editProfile";
        }
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam(value = "orderId", required = false) Integer orderId,
            @RequestParam(value = "orderAmount", required = false) Double orderAmount,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "orderDate", required = false) String orderDate,
            @RequestParam(value = "emailAddress", required = false) String emailAddress,
            HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            ArrayList<Order> orderDetails = adminRepository.getOrderDetails();

            for (Order order : orderDetails) {
                ArrayList<Product> products = order.getProducts();
                ISendOrderStatusEmail email = new SendOrderStatusEmail();
                if (email.sendEmail(orderId, orderAmount, orderDate, emailAddress, orderStatus, products)) {
                    oldSession.setAttribute("msg", "Email is successfully sent!");
                    return "redirect:/admin/orderDetails";
                }
            }
            oldSession.setAttribute("msg", "Some error occurred while sending email! Please try again!");
            return "redirect:/admin/orderDetails";
        }
    }

}
