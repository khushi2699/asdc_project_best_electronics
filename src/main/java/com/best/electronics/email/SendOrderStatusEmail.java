package com.best.electronics.email;

import com.best.electronics.controller.email.EmailController;
import com.best.electronics.model.Product;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;

public class SendOrderStatusEmail implements ISendOrderStatusEmail{
    public Boolean sendEmail(Integer orderId, Double orderAmount, String orderDateString, String emailAddress, String orderStatus, ArrayList<Product> products) {
        try{
            EmailController emailController = new EmailController();
            Session session = emailController.setUpProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            draftEmail(mimeMessage, orderId, orderAmount, orderDateString,emailAddress, orderStatus, products);
            emailController.setMimeMessage(mimeMessage);
            emailController.sendMail();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void draftEmail(MimeMessage mimeMessage, Integer orderId, Double orderAmount,String orderDateString,String toEmail,String orderStatus, ArrayList<Product> products) throws MessagingException {
        String emailSubject = "Order Status - "+ orderStatus;
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(emailSubject);

        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        String productDetails = null;
        for(Product productslist:products){
            productDetails = "Product ID: " +  productslist.getProductId() + "\n\t" + "Product Name: " + productslist.getProductName();
        }
        messageBodyPart.setText("This message is from BestElectronics. Please find the updates on your order!\n Order ID: "+ orderId + "\n" + "Amount Paid: " +  orderAmount + "\n" + "Date of Order: " + orderDateString + "\n" + "Product Details: \n\t" +  productDetails);
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
    }

}
