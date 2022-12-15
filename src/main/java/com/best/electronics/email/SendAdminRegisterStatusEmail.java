package com.best.electronics.email;

import javax.mail.MessagingException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;

public class SendAdminRegisterStatusEmail implements ISendStatusEmail{
    @Override
    public Boolean sendEmail(String emailAddress, HashMap<String, Object> messageDetails) {
        try{
            SendMail sendMail = new SendMail();
            Session session = sendMail.setUpProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            draftEmail(mimeMessage, emailAddress, messageDetails);
            sendMail.setMimeMessage(mimeMessage);
            return sendMail.sendMail();
        } catch (Exception e){
            return false;
        }
    }

    private void draftEmail(MimeMessage mimeMessage, String emailAddress, HashMap<String, Object> messageDetails) throws MessagingException {
        String emailSubject = "Registration Successful as Admin";
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        mimeMessage.setSubject(emailSubject);

        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        messageBodyPart.setText("This message is from BestElectronics. Please find the details for your account!\n " +
                "FirstName: "+ messageDetails.get("firstName") + "\n" +
                "LastName: " +  messageDetails.get("lastName") + "\n" +
                "emailAddress: " + emailAddress+ "\n" + "\n" +
                "Temporary Password: " +  messageDetails.get("password") + "\n" + "\n\n" +
                "**** Kindly immediately change your password using below link:" + "\n\t" + "\n" +
                "Go to below link to change your password: http://localhost:8080/admin/forgotPassword");
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
    }
}
