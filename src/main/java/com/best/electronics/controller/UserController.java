package com.best.electronics.controller;

import com.best.electronics.controller.email.ResetPasswordCombinationValidationHandler;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.login.LoginState;
import com.best.electronics.model.Order;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

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

    @GetMapping("/home")
    public String userHome(Model model){
        return "userLandingPage";
    }

    @PostMapping("/process_login")
    public String processLogin(User user, Model model, HttpServletRequest request) {
        ILoginHandler loginHandler = new UserLoginHandler();
        LoginState loginState = loginHandler.login(user, request);
        model.addAttribute("msg", loginState.getLoginStatus());
        model.addAttribute("user", new User());
        return loginState.getNextPage();
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "userLogin";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(Model model){
        model.addAttribute("login", new User());
        System.out.println("Printing reset Password");
        return "resetPassword";
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

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request){
        ILoginHandler loginHandler = new UserLoginHandler();
        loginHandler.logout(request);

        model.addAttribute("user", new User());
        model.addAttribute("logoutMessage", "Successfully logged out!");
        return "userLogin";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                model.addAttribute("firstName", userDetail.get("firstName"));
                model.addAttribute("lastName", userDetail.get("lastName"));
                model.addAttribute("dateOfBirth", userDetail.get("dob"));
                model.addAttribute("email", userDetail.get("emailAddress"));
                model.addAttribute("address", userDetail.get("address"));
            }

            ArrayList<Order> orderDetails = user.getOrderDetails(id, databasePersistence);
            model.addAttribute("orders", orderDetails);
            return "userProfile";
        }
       return "userLogin";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            String updatedStatus = (String) oldSession.getAttribute("updatedStatus");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("updatedStatus");
            }

            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail == null){
                model.addAttribute("updatedStatus", "Some exception occurred! Please try again!");
            }

            model.addAttribute("firstName", userDetail.get("firstName"));
            model.addAttribute("lastName", userDetail.get("lastName"));
            model.addAttribute("dateOfBirth", userDetail.get("dob"));
            model.addAttribute("email", userDetail.get("emailAddress"));
            model.addAttribute("address", userDetail.get("address"));
            model.addAttribute("user", user);
            model.addAttribute("updatedStatus", updatedStatus);
            return "editUserDetails";
        }
        return "userLogin";
    }

    @PostMapping("/update_profile")
    public String processUpdateProfile(User user, HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            String message = user.updateUserDetails(databasePersistence);
            oldSession.setAttribute("updatedStatus", message);
            return "redirect:/user/editProfile";
        }
        return "userLogin";
    }


}
