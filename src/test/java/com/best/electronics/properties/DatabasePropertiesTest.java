package com.best.electronics.properties;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabasePropertiesTest {

    @Test
    public void checkPropertiesConfigured(){
        DatabaseProperties databaseProperties = new DatabaseProperties();

        Assertions.assertEquals("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_4_DEVINT", databaseProperties.getUrl());
        Assertions.assertEquals("CSCI5308_4_DEVINT_USER", databaseProperties.getUsername());
        Assertions.assertEquals("fFhBcjh63E", databaseProperties.getPassword());
    }
}
