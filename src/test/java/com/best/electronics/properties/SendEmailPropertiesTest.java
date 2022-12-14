package com.best.electronics.properties;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendEmailPropertiesTest {
    @Test
    public void checkPropertiesConfigured(){
        SendMailProperties sendMailProperties = new SendMailProperties();

        Assertions.assertEquals("smtp.gmail.com", sendMailProperties.getEmailHost());
        Assertions.assertEquals("best.electronics.dal@gmail.com", sendMailProperties.getFromUser());
        Assertions.assertEquals("qwxgvjnfuaalsewc", sendMailProperties.getFromUserPassword());
    }
}
