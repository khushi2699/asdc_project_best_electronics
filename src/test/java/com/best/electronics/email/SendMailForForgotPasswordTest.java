package com.best.electronics.email;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailForForgotPasswordTest {
    SendMailForForgotPassword sendMailForForgotPassword;

    @Before
    public void init(){
        sendMailForForgotPassword = new SendMailForForgotPassword();
    }

    @Test
    public void draftEmailSuccessTest(){

    }

}
