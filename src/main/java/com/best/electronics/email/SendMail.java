package com.best.electronics.email;

import com.best.electronics.properties.EmailProperties;
import com.best.electronics.properties.SendMailProperties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void setMimeMessage(MimeMessage mimeMessage){
        this.mimeMessage = mimeMessage;
    }

    public Session setUpProperties(){
        Properties properties = new Properties();
        EmailProperties emailProperties = new EmailProperties();
        properties.put("mail.smtp.port",emailProperties.getSmtp());
        properties.put("mail.smtp.auth",emailProperties.getAuth());
        properties.put("mail.smtp.starttls.enable",emailProperties.getStarttls_enable());
        properties.put("mail.smtp.ssl.protocols", emailProperties.getSsl_protocols());
        newSession = Session.getDefaultInstance(properties,null);
        return newSession;
    }

    public boolean sendMail() throws MessagingException {
        SendMailProperties sendMailProperties = new SendMailProperties();
        Transport transport = newSession.getTransport("smtp");
        transport.connect(sendMailProperties.getEmailHost(), sendMailProperties.getFromUser(), sendMailProperties.getFromUserPassword());
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        return true;
    }
}
