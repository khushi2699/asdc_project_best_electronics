package com.best.electronics.sendEmailOrderStatus;

import com.best.electronics.email.SendMail;
import com.best.electronics.email.SendOrderStatusEmail;
import com.best.electronics.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static org.mockito.Mockito.*;

public class SendOrderStatusEmailTest {
    public static final String EMAIL_ADDRESS = "test@gmail.com";

    @Test
    public void SendEmailSuccess() {
        HashMap<String, Object> messageDetails = new HashMap<String, Object>();
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = new Product();
        product.setProductQuantity(100);
        product.setProductName("Keyboard");
        product.setProductId(1);
        products.add(product);

        messageDetails.put("orderId", "1");
        messageDetails.put("orderAmount", "100");
        messageDetails.put("orderDate", "2022-02-10");
        messageDetails.put("orderStatus", "Order Shipped");
        messageDetails.put("products", products);

        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try (MockedConstruction<SendMail> mocked = Mockito.mockConstruction(SendMail.class,
                (mock, context) -> {
                    when(mock.setUpProperties()).thenReturn(Session.getDefaultInstance(new Properties(), null));
                    doNothing().when(mock).setMimeMessage(mimeMessage);
                    when(mock.sendMail()).thenReturn(true);
                })) {
            SendOrderStatusEmail sendEmail = new SendOrderStatusEmail();
            Boolean status = sendEmail.sendEmail(EMAIL_ADDRESS, messageDetails);

            Assertions.assertEquals(true, status);
        }
    }

    @Test
    public void SendEmailFail() {
        HashMap<String, Object> messageDetails = new HashMap<String, Object>();
        ArrayList<Product> products = new ArrayList<Product>();
        Product product = new Product();
        product.setProductQuantity(100);
        product.setProductName("Keyboard");
        product.setProductId(1);
        products.add(product);

        messageDetails.put("orderId", "1");
        messageDetails.put("orderAmount", "100");
        messageDetails.put("orderDate", "2022-02-10");
        messageDetails.put("orderStatus", "Order Shipped");
        messageDetails.put("products", products);
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try (MockedConstruction<SendMail> mocked = Mockito.mockConstruction(SendMail.class,
                (mock, context) -> {
                    when(mock.setUpProperties()).thenReturn(Session.getDefaultInstance(new Properties(), null));
                    doNothing().when(mock).setMimeMessage(mimeMessage);
                    doThrow().when(mock).sendMail();
                })) {
            SendOrderStatusEmail sendEmail = new SendOrderStatusEmail();
            Boolean status = sendEmail.sendEmail(EMAIL_ADDRESS, messageDetails);

            Assertions.assertEquals(false, status);
        }
    }
}


