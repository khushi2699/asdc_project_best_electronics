package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.forgotPassword.GetCode;
import com.best.electronics.repository.PasswordRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SendMailForForgotPassword implements GetCode {

    Session newSession = null;
    MimeMessage mimeMessage = null;

    @Override
    public void generateCode(String medium) throws Exception {
        emailControl(medium);
    }

    public void emailControl (String email) throws Exception {
        GenerateRandomNumber generateRandomNumber = GenerateRandomNumber.getInstance();
        int randomNumber = generateRandomNumber.generateRandomNumber();
        SendMail sendMail = new SendMail();
        newSession = sendMail.setUpProperties();
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
        ArrayList<Map<String, Object>> result= passwordRepository.getEmailCheck(email);
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


}
