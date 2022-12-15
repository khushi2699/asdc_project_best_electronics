package com.best.electronics.properties;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailPropertiesTest {
    @Test
    public void checkEmailPropertiesConfigured(){
        EmailProperties emailProperties = new EmailProperties();

        Assertions.assertEquals("TLSv1.2", emailProperties.ssl_protocols);
        Assertions.assertEquals("true", emailProperties.starttls_enable);
        Assertions.assertEquals("true", emailProperties.auth);
        Assertions.assertEquals("587", emailProperties.smtp);
    }
}
