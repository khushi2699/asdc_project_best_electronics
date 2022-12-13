package com.best.electronics.report.sender;

import com.best.electronics.sendEmail.SendMail;
import com.best.electronics.properties.ReportProperties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SmtpEmailSendReport implements ISendReport{

    @Override
    public Boolean sendReport(String emailAddress, String fileName) {
        try{
            SendMail sendMail = new SendMail();
            Session session = sendMail.setUpProperties();

            MimeMessage mimeMessage = new MimeMessage(session);

            draftEmail(mimeMessage, emailAddress, fileName);
            sendMail.setMimeMessage(mimeMessage);
            sendMail.sendMail();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void draftEmail(MimeMessage mimeMessage, String toEmail, String fileName) throws MessagingException {
        String emailSubject = "Report for " + fileName;
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(emailSubject);

        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        messageBodyPart.setText("This message is from BestElectronics. Please find the required report in the attachment!");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(getFilePath() + fileName);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);
    }

    private String getFilePath() {
        ReportProperties reportProperties = new ReportProperties();
        return reportProperties.getFileLocation();
    }

}
