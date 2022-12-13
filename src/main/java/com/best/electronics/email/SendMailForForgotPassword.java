package com.best.electronics.email;

import com.best.electronics.controller.email.EmailControllerPinResetStore;
import com.best.electronics.controller.email.EmailControllerPinStoreHandler;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SendMailForForgotPassword {

    Session newSession = null;
    MimeMessage mimeMessage = null;


    public void emailControl (String email) throws Exception {
        int randomNumber = generateRandomNumber(); //generating a random number
        SendMail sendMail = new SendMail();
        newSession = sendMail.setUpProperties();
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(email);
        ArrayList<Map<String, Object>> result= new ArrayList<>();
        result = databasePersistence.loadData("{call get_email_check(?)}",tokenDetails);
        if(result.size()== 1){
            //checking if email is valid or not, if valid set random token, draft email and send.
            saveToDB(randomNumber,email);
            draftEmail(randomNumber,email);
            sendMail.setMimeMessage(mimeMessage);
            sendMail.sendMail();
        }
    }

    public MimeMessage draftEmail(int randomNumber, String email) throws MessagingException {
        String toEmail = email;
        String emailSubject = "Token for new password request";
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
    public int generateRandomNumber(){
        Random randomNumber = new Random();
        int number =  randomNumber.nextInt(999999);
        return number;
    }
}
