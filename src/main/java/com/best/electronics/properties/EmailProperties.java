package com.best.electronics.properties;

import java.util.Properties;

public class EmailProperties {

        String smtp;
        String auth;
        String starttls_enable;
        String ssl_protocols;

        public EmailProperties(){
            PropertiesLoader propertyLoader = PropertiesLoader.getInstance();
            Properties properties = propertyLoader.getProperties();
            smtp = properties.getProperty("app.email.mail.smtp.port");
            auth = properties.getProperty("app.email.mail.smtp.auth");
            starttls_enable = properties.getProperty("app.email.mail.smtp.starttls.enable");
            ssl_protocols = properties.getProperty("app.email.mail.smtp.ssl.protocols");
        }

        public String getSmtp() {
            return smtp;
        }

        public String getAuth() {
            return auth;
        }

        public String getStarttls_enable(){return starttls_enable;}

        public String getSsl_protocols() {return ssl_protocols;}
}
