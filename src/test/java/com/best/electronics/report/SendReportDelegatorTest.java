package com.best.electronics.report;

import com.best.electronics.email.SendMail;
import com.best.electronics.report.sender.ISendReport;
import com.best.electronics.report.sender.SendReportDelegator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class SendReportDelegatorTest {

    public static final String EMAIL_ADDRESS = "test@gmail.com";
    public static final String FILE = "test.csv";


    @Test
    public void smtpEmailSendReportSuccess(){
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try (MockedConstruction<SendMail> mocked = Mockito.mockConstruction(SendMail.class,
                (mock, context) -> {when(mock.setUpProperties()).thenReturn(Session.getDefaultInstance(new Properties(), null));
                    doNothing().when(mock).setMimeMessage(mimeMessage);
                   when(mock.sendMail()).thenReturn(true);})) {
            SendReportDelegator sendReportDelegator = new SendReportDelegator();
            ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
            Boolean status = sendReport.sendReport(EMAIL_ADDRESS, FILE);

            Assertions.assertEquals(true, status);
        }
    }

    @Test
    public void smtpEmailSendReportFail(){
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try (MockedConstruction<SendMail> mocked = Mockito.mockConstruction(SendMail.class,
                (mock, context) -> {when(mock.setUpProperties()).thenReturn(Session.getDefaultInstance(new Properties(), null));
                    doNothing().when(mock).setMimeMessage(mimeMessage);
                    doThrow().when(mock).sendMail();})) {
            SendReportDelegator sendReportDelegator = new SendReportDelegator();
            ISendReport sendReport = sendReportDelegator.identifySender("SMTP");
            Boolean status = sendReport.sendReport(EMAIL_ADDRESS, FILE);

            Assertions.assertEquals(false, status);
        }
    }
}
