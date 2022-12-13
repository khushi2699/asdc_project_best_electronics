package com.best.electronics.properties;

import java.util.Properties;

public class SendMailProperties {

    String fromUser;
    String fromUserPassword;
    String emailHost;

    public SendMailProperties(){
        PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
        Properties properties = propertyLoader.getProperties();
        fromUser = properties.getProperty("app.email.send.from");
        fromUserPassword = properties.getProperty("app.email.send.password");
        emailHost = properties.getProperty("app.email.send.emailhost");
    }

    public String getEmailHost() {
        return emailHost;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getFromUserPassword() {
        return fromUserPassword;
    }
}

