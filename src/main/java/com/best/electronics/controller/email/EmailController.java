package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class EmailController {

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        model.addAttribute("login", new User());
        return "forgotPassword";
    }

    @PostMapping("/enterNewPassword")
    public String enterNewPassword(@ModelAttribute User user, Model model) throws NoSuchAlgorithmException {
        model.addAttribute("login", new User());
        ChangePasswordHandler changePasswordHandler = new ChangePasswordHandler();
        String status = changePasswordHandler.storeNewPassword(user.getPassword(), user.getConfirmPassword(), user.getEmailAddress());
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

    @PostMapping("/getCode")
    public String getCode(@ModelAttribute User user, Model model) throws Exception {
        System.out.println("Email address" + user.getEmailAddress());
        emailControl(user.getEmailAddress());
        model.addAttribute("login", new User());
        model.addAttribute("msg", "Password reset link and token will be sent to you email if the email exists!");
        return "forgotPassword";
    }

    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void setMimeMessage(MimeMessage mimeMessage){
        this.mimeMessage = mimeMessage;
    }

    public void emailControl (String email) throws Exception {
        EmailController emailController = new EmailController();
        int randomNumber = emailController.generateRandomNumber(); //generating a random number
        emailController.setUpProperties();

        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(email);
        ArrayList<Map<String, Object>> result= new ArrayList<>();

        result = databasePersistence.loadData("{call get_email_check(?)}",tokenDetails);
        if(result.size()== 1){
            emailController.draftEmail(randomNumber,email);
            emailController.sendMail();
        }
    }

    public int generateRandomNumber(){
        Random randomNumber = new Random();
        int number =  randomNumber.nextInt(999999);
        return number;
    }

    public Session setUpProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        newSession = Session.getDefaultInstance(properties,null);
        return newSession;
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
    public void sendMail() throws MessagingException {
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