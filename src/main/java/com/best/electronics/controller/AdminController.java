package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.AdminLoginHandler;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.properties.AdminProperties;
import com.best.electronics.register.AdminRegisterHandler;
import com.best.electronics.state.State;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.model.User;
import com.best.electronics.model.Admin;
import com.best.electronics.model.Product;
import com.best.electronics.model.Order;
import com.best.electronics.register.IRegisterHandler;
import com.best.electronics.repository.AdminRepository;
import com.best.electronics.repository.ProductRepository;
import com.best.electronics.repository.UserRepository;
import com.best.electronics.email.ISendOrderStatusEmail;
import com.best.electronics.email.SendOrderStatusEmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/process_registration")
    public String processRegistration(Admin admin, Model model){
        IRegisterHandler registerHandler = new AdminRegisterHandler();
        State registerState = registerHandler.register(admin, new HashMap<>());
        model.addAttribute("msg", registerState.getStatus());
        return registerState.getNextPage();
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("admin", new Admin());
        return "adminRegistrationForm";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin", new Admin());
        return "adminLogin";
    }

    @PostMapping("/process_login")
    public String processLogin(Admin admin, Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new AdminLoginHandler();
        State loginState = loginHandler.login(admin, request);
        model.addAttribute("msg", loginState.getStatus());
        model.addAttribute("admin", new Admin());

        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            AdminProperties adminProperties = new AdminProperties();
            if(oldSession.getAttribute("id") == adminProperties.getId()){
                model.addAttribute("isSuperAdmin", true);
            }

        }
        return loginState.getNextPage();
    }

    @GetMapping("/adminHome")
    public String adminHome(Model model,HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            AdminProperties adminProperties = new AdminProperties();
            if(oldSession.getAttribute("id") == adminProperties.getId()){
                model.addAttribute("isSuperAdmin", true);
            }
            return "adminLandingPage";
        }
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
    public String orderDetails(Order order, Model model, HttpServletRequest request) throws Exception {
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

    @GetMapping("/adminList")
    public String getAdminsList(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            String updatedStatus = (String) oldSession.getAttribute("msg");
            Integer id = (Integer) oldSession.getAttribute("id");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("msg");
            }
            model.addAttribute("msg",updatedStatus);

            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            ArrayList<Map<String, Object>> adminDetails = adminRepository.getAllAdmins(id);
            model.addAttribute("adminDetails", adminDetails);
            return "adminList";
        }
    }

    @PostMapping("/deleteAdmin/{adminId}")
    public String deleteAdmin(@PathVariable Integer adminId, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null) {
            return "adminLogin";
        }else{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            AdminRepository adminRepository = new AdminRepository(databasePersistence);
            if(adminRepository.deleteAdmin(adminId)){
                oldSession.setAttribute("msg","Admin Deleted Successfully!");
                return "redirect:/admin/adminList";
            }
            oldSession.setAttribute("msg","Some error occurred while deleting the Admin. Please try again!");
            return "redirect:/admin/adminList";
        }
    }

    @GetMapping("/users")
    public String adminUsers(Model model){
        IDatabasePersistence db = new MySQLDatabasePersistence();
        UserRepository userRepository = new UserRepository(db);
        ArrayList<Map<String, Object>> userList = userRepository.getAllUsersDetails();
        if(userList.isEmpty()){
            return "redirect:/admin/adminHome";
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
        if(productCategoryList.isEmpty()){
            return "redirect:/admin/adminHome";
        }else {
            model.addAttribute("listProductCategory", productCategoryList);
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
        HttpServletRequest request){
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
