package com.best.electronics.email;

import com.best.electronics.properties.EmailProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import javax.mail.Session;
import java.util.Properties;


@SpringBootTest
public class SendMailTest {

    SendMail sendMail;

    @Before
    public void init(){
        sendMail = new SendMail();
    }

    @Test
    public void setUpPropertiesTestSuccess(){
        Properties properties = new Properties();
        EmailProperties emailProperties = new EmailProperties();
        properties.put("mail.smtp.port",emailProperties.getSmtp());
        properties.put("mail.smtp.auth",emailProperties.getAuth());
        properties.put("mail.smtp.starttls.enable",emailProperties.getStarttls_enable());
        properties.put("mail.smtp.ssl.protocols", emailProperties.getSsl_protocols());
        Session newSession= Session.getDefaultInstance(properties,null);
        Session newSession1 = sendMail.setUpProperties();
        Assertions.assertEquals(newSession,newSession1);
    }

    @Test
    public void setUpPropertiesTestFailure() {
        Properties properties = new Properties();
        EmailProperties emailProperties = new EmailProperties();
        properties.put("mail.smtp.port",542);
        properties.put("mail.smtp.auth",emailProperties.getAuth());
        properties.put("mail.smtp.starttls.enable",emailProperties.getStarttls_enable());
        properties.put("mail.smtp.ssl.protocols", emailProperties.getSsl_protocols());
        Session newSession= Session.getDefaultInstance(properties,null);
        Session newSession1 = sendMail.setUpProperties();
        Assertions.assertEquals(newSession,newSession1);
    }
}
