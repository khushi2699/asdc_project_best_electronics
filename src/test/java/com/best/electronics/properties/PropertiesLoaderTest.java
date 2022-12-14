package com.best.electronics.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Properties;

@SpringBootTest
public class PropertiesLoaderTest {

    private static ArrayList<String> properties;

    @BeforeEach
    public void init(){
        properties = new ArrayList<>();
        properties.add("app.database.url");
        properties.add("app.database.username");
        properties.add("app.database.password");
        properties.add("app.database.minIdle");
        properties.add("app.database.maxIdle");
        properties.add("app.database.maxOpenPreparedStatements");
        properties.add("app.report.excel.sheet");
        properties.add("app.email.mail.smtp.port");
        properties.add("app.email.mail.smtp.auth");
        properties.add("app.email.mail.smtp.starttls.enable");
        properties.add("app.email.mail.smtp.ssl.protocols");
        properties.add("app.email.send.from");
        properties.add("app.email.send.password");
        properties.add("app.email.send.emailhost");
        properties.add("app.admin.id");
        properties.add("app.admin.role");
        properties.add("app.filter.excluded.urls.user");
        properties.add("app.filter.excluded.urls.admin");
        properties.add("app.filter.excluded.urls.common");
    }

    @Test
    public void checkPropertiesConfiguredSuccess(){
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        Properties configuredProperties = propertiesLoader.getProperties();

        for(String property: properties){
            Assertions.assertNotNull(configuredProperties.get(property));
        }
    }

    @Test
    public void checkPropertiesConfiguredFail(){
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        Properties configuredProperties = propertiesLoader.getProperties();

        Assertions.assertNull(configuredProperties.get("app.filter.excluded.urls"));
    }
}
