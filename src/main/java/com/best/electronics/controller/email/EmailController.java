package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class EmailController {

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        model.addAttribute("login", new Login());
        return "forgotPassword";
    }

    @PostMapping("/enterNewPassword")
    public String enterNewPassword(@ModelAttribute Login login, Model model) throws NoSuchAlgorithmException {
        model.addAttribute("login", new Login());
        ChangePasswordHandler changePasswordHandler = new ChangePasswordHandler();
        String status = changePasswordHandler.storeNewPassword(login.getPassword(), login.getConfirmPassword(), login.getEmailAddress());
        if(status.equalsIgnoreCase("Password changed")){
            model.addAttribute("msg", "Password changed. Click here to login! http://localhost:8080/user/login");
            return "changePassword";

        }else if (status.equalsIgnoreCase("No match")) {
            model.addAttribute("msg", "Password and Confirm Password does not match");
            return "changePassword";
        }
        else if (status.equalsIgnoreCase("Invalid pattern")){
            model.addAttribute("msg", "Invalid password pattern");
            return "changePassword";
        }
        else {
            return null;
        }
    }

    @GetMapping("/userLogin")
    public String userLogin(Model model){
        model.addAttribute("user", new Login());
        return "login";
    }

    @PostMapping("/getCode")
    public String getCode(@ModelAttribute Login login, Model model) throws MessagingException {
        System.out.println("Email address" + login.getEmailAddress());
        emailControl(login.getEmailAddress());
        model.addAttribute("login", new Login());
        model.addAttribute("msg", "Password reset link and token will be sent to you email if the email exists!");
        return "forgotPassword";
    }

    Session newSession = null;
    MimeMessage mimeMessage = null;
    public void emailControl (String email) throws MessagingException {
        EmailController emailController = new EmailController();
        int randomNumber = emailController.generateRandomNumber(); //generating a random number
        emailController.setUpProperties();
        emailController.draftEmail(randomNumber,email);
        emailController.sendMail();
    }

    public int generateRandomNumber(){
        Random randomNumber = new Random();
        int number =  randomNumber.nextInt(999999);
        return number;
    }

    public void setUpProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        newSession = Session.getDefaultInstance(properties,null);
    }

    private MimeMessage draftEmail(int randomNumber, String email) throws MessagingException {
        String toEmail = email;
        String emailSubject = "Token for new password request";
        saveToDB(randomNumber,email);
        mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(emailSubject);
        mimeMessage.setText("We have received a password change request from your ID. Below is your code: "+randomNumber+". " +
                "Go to below link to change your password: http://localhost:8080/user/resetPassword");
        return mimeMessage;

    }

    private void saveToDB(int randomNumber, String email){
        EmailControllerPinResetStore emailControllerPinResetStore = new EmailControllerPinStoreHandler();
        emailControllerPinResetStore.storePinToDB(randomNumber,email);
    }
    private void sendMail() throws MessagingException {
        String fromUser = "khshah2699@gmail.com";
        String fromUserPassword = "negftgxahztglvpm";
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        System.out.println(mimeMessage);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!!!");
    }
}