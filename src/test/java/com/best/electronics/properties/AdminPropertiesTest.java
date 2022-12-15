package com.best.electronics.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminPropertiesTest {

    @Test
    public void checkAdminPropertiesConfigured(){
        AdminProperties adminProperties = new AdminProperties();

        Assertions.assertNotNull(adminProperties.getId());
        Assertions.assertNotNull(adminProperties.getRole());
    }
}
