package com.best.electronics.properties;

import java.util.Properties;

public class AdminProperties {

    Integer id;

    String role;

    public AdminProperties(){
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        id = Integer.valueOf(properties.getProperty("app.admin.id"));
        role = properties.getProperty("app.admin.role");
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
