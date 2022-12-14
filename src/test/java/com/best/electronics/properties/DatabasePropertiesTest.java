package com.best.electronics.properties;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabasePropertiesTest {

    @Test
    public void checkDBPropertiesConfigured(){
        DatabaseProperties databaseProperties = new DatabaseProperties();

        Assertions.assertNotNull(databaseProperties.getUrl());
        Assertions.assertNotNull(databaseProperties.getUsername());
        Assertions.assertNotNull(databaseProperties.getPassword());
    }
}
