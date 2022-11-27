package com.best.electronics.properties;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private final Properties configurationProperties = new Properties();

    private PropertiesLoader() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try{
            configurationProperties.load(inputStream);
        } catch (Exception e){
            System.out.println("Exception occurred while reading the properties");
        }

    }

    private static final class PropertyLoaderHolder {
        static final PropertiesLoader propertyLoader = new PropertiesLoader();
    }

    public static PropertiesLoader getInstance(){
        return PropertyLoaderHolder.propertyLoader;
    }

    public Properties getProperties(){
        return configurationProperties;
    }


}
