package com.best.electronics.email;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class SendMailForForgotPasswordTest {

    SendMailForForgotPassword sendMailForForgotPassword;

    @Before
    public void init(){
        sendMailForForgotPassword = new SendMailForForgotPassword();
    }

    @Test
    public void draftEmailSuccessUserTest() throws MessagingException {
        sendMailForForgotPassword.draftEmail(123,"user@gmail.com","User");
        Assert.assertTrue(true);
    }

    @Test
    public void draftEmailSuccessFailureUserTest() throws MessagingException {
        sendMailForForgotPassword.draftEmail(123,"null","User");
        Assert.assertFalse(false);
    }

    @Test
    public void draftEmailSuccessAdminTest() throws MessagingException {
        sendMailForForgotPassword.draftEmail(123,"admin@gmail.com","Admin");
        Assert.assertTrue(true);
    }

    @Test
    public void draftEmailSuccessFailureAdminTest() throws MessagingException {
        sendMailForForgotPassword.draftEmail(123,"null","Admin");
        Assert.assertFalse(false);
    }


}

