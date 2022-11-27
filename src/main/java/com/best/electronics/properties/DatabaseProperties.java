package com.best.electronics.properties;

import java.util.Properties;

public class DatabaseProperties {

    private final String url;
    private final String username;
    private final String password;

    public DatabaseProperties(){
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        url = properties.getProperty("app.database.url");
        username = properties.getProperty("app.database.username");
        password = properties.getProperty("app.database.password");
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
