package com.best.electronics.properties;

import java.util.Properties;

public class DatabaseProperties {

    private final String url;
    private final String username;
    private final String password;

    private final int minIdle;

    private final int maxIdle;

    private final int maxOpenPreparedStatements;

    public DatabaseProperties(){
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        url = properties.getProperty("app.database.url");
        username = properties.getProperty("app.database.username");
        password = properties.getProperty("app.database.password");
        minIdle = Integer.parseInt(properties.getProperty("app.database.minIdle"));
        maxIdle = Integer.parseInt(properties.getProperty("app.database.maxIdle"));
        maxOpenPreparedStatements = Integer.parseInt(properties.getProperty("app.database.maxOpenPreparedStatements"));
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

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }
}
