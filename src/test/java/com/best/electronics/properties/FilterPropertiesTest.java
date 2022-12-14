package com.best.electronics.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FilterPropertiesTest {

    @Test
    public void checkFilterPropertiesConfigured(){
        FilterProperties filterProperties = new FilterProperties();

        Assertions.assertNotNull(filterProperties.getAdminExcludedUrls());
        Assertions.assertNotNull(filterProperties.getCommonExcludedUrls());
        Assertions.assertNotNull(filterProperties.getUserExcludedUrls());
    }
}
