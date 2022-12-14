package com.best.electronics.forgotPassword;

import com.best.electronics.email.SendMailForForgotPassword;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetPasswordFactoryTest {

    ResetPasswordFactory resetPasswordFactory;

    @Before
    public void init(){
        resetPasswordFactory = new ResetPasswordFactory();
    }

    @Test
    public void sendCodeThroughSuccessTest(){
        GetCode getCode = resetPasswordFactory.sendCodeThrough("Email");
        Boolean value = getCode.getClass().isInstance(new SendMailForForgotPassword());
        Assertions.assertEquals(true , value);
    }

    @Test (expected = IllegalArgumentException.class)
    public void sendCodeThroughFailureTest(){
        GetCode getCode = resetPasswordFactory.sendCodeThrough("SMS");
        Boolean value = getCode.getClass().isInstance(new IllegalArgumentException());
        Assertions.assertEquals(false,value);
    }
}
