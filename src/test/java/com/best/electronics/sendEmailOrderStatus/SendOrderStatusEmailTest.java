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

        @Test
        public void checkSendEmailSuccess(){

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
            SendOrderStatusEmail sendEmail = new SendOrderStatusEmail();
            try (MockedConstruction<SendOrderStatusEmail> mocked = Mockito.mockConstruction(SendOrderStatusEmail.class,
                    (mock, context) -> when(mock.sendEmail("admin@gmail.com", messageDetails)).thenReturn(true))) {
                boolean status = sendEmail.sendEmail("admin@gmail.com", messageDetails);
                Assertions.assertEquals(true,status);
            }
        }
        @org.junit.jupiter.api.Test
        public void checkSendEmailFailure(){

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
            SendOrderStatusEmail sendEmail = new SendOrderStatusEmail();
            try (MockedConstruction<SendOrderStatusEmail> mocked = Mockito.mockConstruction(SendOrderStatusEmail.class,
                    (mock, context) -> doThrow().when(mock).sendEmail("admin@gmail.com", messageDetails))) {
                boolean status = sendEmail.sendEmail("null", messageDetails);
                Assertions.assertEquals(false,status);
            }
        }

//        @org.junit.jupiter.api.Test
//        public void draftEmailSuccessTest() throws MessagingException {
//            HashMap<String, Object> messageDetails = new HashMap<String, Object>();
//            ArrayList<Product> products = new ArrayList<Product>();
//            SendOrderStatusEmail sendmail = new SendOrderStatusEmail();
//            SendMail sendMail = new SendMail();
//            Session session = sendMail.setUpProperties();
//            MimeMessage mimeMessage = new MimeMessage(session);
//            Product product = new Product();
//            product.setProductQuantity(100);
//            product.setProductName("Keyboard");
//            product.setProductId(1);
//            products.add(product);
//
//            messageDetails.put("orderId", "1");
//            messageDetails.put("orderAmount", "100");
//            messageDetails.put("orderDate", "2022-02-10");
//            messageDetails.put("orderStatus", "Order Shipped");
//            messageDetails.put("products", products);
//
//            sendmail.draftEmail(mimeMessage,"user@gmail.com",messageDetails);
//            Assert.assertTrue(true);
//        }
//
//        @org.junit.jupiter.api.Test
//        public void draftEmailFailureTest() throws MessagingException {
//            HashMap<String, Object> messageDetails = new HashMap<String, Object>();
//            ArrayList<Product> products = new ArrayList<Product>();
//            SendOrderStatusEmail sendmail = new SendOrderStatusEmail();
//            SendMail sendMail = new SendMail();
//            Session session = sendMail.setUpProperties();
//            MimeMessage mimeMessage = new MimeMessage(session);
//            Product product = new Product();
//            product.setProductQuantity(100);
//            product.setProductName("Keyboard");
//            product.setProductId(1);
//            products.add(product);
//
//            messageDetails.put("orderId", "1");
//            messageDetails.put("orderAmount", "100");
//            messageDetails.put("orderDate", "2022-02-10");
//            messageDetails.put("orderStatus", "Order Shipped");
//            messageDetails.put("products", products);
//
//            sendmail.draftEmail(mimeMessage,"null",messageDetails);
//            Assert.assertFalse(false);
//        }

    }

