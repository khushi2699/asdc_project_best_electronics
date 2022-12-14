package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.ILoginHandler;
import com.best.electronics.state.State;
import com.best.electronics.login.UserLoginHandler;
import com.best.electronics.model.Order;
import com.best.electronics.model.User;
import com.best.electronics.register.IRegisterHandler;
import com.best.electronics.register.UserRegisterHandler;
import com.best.electronics.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/process_registration")
    public String processRegistration(User user, Model model){
        IRegisterHandler registerHandler = new UserRegisterHandler();
        Map<String, Object> specificParameters = new HashMap<>();
        specificParameters.put("dob", user.getDateOfBirth());
        specificParameters.put("address", user.getAddress());
        State registerState = registerHandler.register(user, specificParameters);
        model.addAttribute("msg", registerState.getStatus());
        return registerState.getNextPage();
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "userRegistrationForm";
    }

    @GetMapping("/home")
    public String userHome(){
        return "userLandingPage";
    }

    @PostMapping("/process_login")
    public String processLogin(User user, Model model, HttpServletRequest request) {
        ILoginHandler loginHandler = new UserLoginHandler();
        State loginState = loginHandler.login(user, request);
        model.addAttribute("msg", loginState.getStatus());
        model.addAttribute("user", new User());
        return loginState.getNextPage();
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "userLogin";
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
        if(oldSession == null){
            return "userLogin";
        }else{
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
            if(userDetail != null){
                model.addAttribute("firstName", userDetail.get("firstName"));
                model.addAttribute("lastName", userDetail.get("lastName"));
                model.addAttribute("dateOfBirth", userDetail.get("dob"));
                model.addAttribute("email", userDetail.get("emailAddress"));
                model.addAttribute("address", userDetail.get("address"));
            }

            ArrayList<Order> orderDetails = userRepository.getUserOrderDetails(id);
            model.addAttribute("orders", orderDetails);
            return "userProfile";
        }
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "userLogin";
        }else{
            Integer id = (Integer) oldSession.getAttribute("id");
            String updatedStatus = (String) oldSession.getAttribute("updatedStatus");
            System.out.println(updatedStatus);
            if(updatedStatus != null){
                oldSession.removeAttribute("updatedStatus");
            }

            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
            if(userDetail == null){
                model.addAttribute("updatedStatus", "Some exception occurred! Please try again!");
                return "editUserDetails";
            }else{
                model.addAttribute("firstName", userDetail.get("firstName"));
                model.addAttribute("lastName", userDetail.get("lastName"));
                model.addAttribute("dateOfBirth", userDetail.get("dob"));
                model.addAttribute("email", userDetail.get("emailAddress"));
                model.addAttribute("address", userDetail.get("address"));
            }

            User user = new User();
            model.addAttribute("user", user);
            model.addAttribute("updatedStatus", updatedStatus);
            return "editUserDetails";
        }
    }

    @PostMapping("/update_profile")
    public String processUpdateProfile(User user, HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null){
            return "userLogin";
        }else{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            String message = userRepository.updateUserDetails(user);
            oldSession.setAttribute("updatedStatus", message);
            return "redirect:/user/editProfile";
        }
    }

}
