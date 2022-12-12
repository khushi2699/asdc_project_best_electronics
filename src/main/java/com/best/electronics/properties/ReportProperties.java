package com.best.electronics.properties;

import java.util.Properties;

public class ReportProperties {

    String fileLocation;

    String excelSheetName;

    public ReportProperties(){
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        fileLocation = properties.getProperty("app.report.folder.location");
        excelSheetName = properties.getProperty("app.report.excel.sheet");
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public String getExcelSheetName() {
        return excelSheetName;
    }
}
