package com.best.electronics.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportPropertiesTest {

    @Test
    public void checkReportPropertiesConfigured(){
        ReportProperties reportProperties = new ReportProperties();

        Assertions.assertNotNull(reportProperties.getFileLocation());
        Assertions.assertNotNull(reportProperties.getExcelSheetName());
    }
}
