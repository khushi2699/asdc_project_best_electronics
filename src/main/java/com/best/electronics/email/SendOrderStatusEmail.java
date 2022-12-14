package com.best.electronics.email;

import com.best.electronics.model.Product;
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
import java.util.ArrayList;

public class SendOrderStatusEmail implements ISendStatusEmail{

    @Override
    public Boolean sendEmail(String emailAddress, HashMap<String, Object> messageDetails) {
        try{
            SendMail sendMail = new SendMail();
            Session session = sendMail.setUpProperties();
            MimeMessage mimeMessage = new MimeMessage(session);
            draftEmail(mimeMessage, emailAddress, messageDetails);
            sendMail.setMimeMessage(mimeMessage);
            sendMail.sendMail();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void draftEmail(MimeMessage mimeMessage, String toEmail, HashMap<String, Object> messageDetails) throws MessagingException {
        String emailSubject = "Order Status - "+ messageDetails.get("orderStatus");
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(emailSubject);

        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        String productDetails = "";
        ArrayList<Product> products = (ArrayList<Product>) messageDetails.get("products");
        for(Product product: products){
            productDetails = productDetails + "\n\t" + "Product ID: " +  product.getProductId() + "\n\t" + "Product Name: " + product.getProductName();

        }
        messageBodyPart.setText("This message is from BestElectronics. Please find the updates on your order!\n Order ID: "+ messageDetails.get("orderId") + "\n" + "Amount Paid: " +  messageDetails.get("orderAmount") + "\n" + "Date of Order: " + messageDetails.get("orderDate") + "\n" + "Product Details: \n\t" +  productDetails + "\n" + "We have successfully accepted your order and the order has been placed.");
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
    }


}
