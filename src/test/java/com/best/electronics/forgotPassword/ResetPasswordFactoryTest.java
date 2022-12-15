package com.best.electronics.forgotPassword;

import com.best.electronics.email.SendMailForForgotPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetPasswordFactoryTest {

    static ResetPasswordFactory resetPasswordFactory;

    @BeforeAll
    public static void init(){
        resetPasswordFactory = new ResetPasswordFactory();
    }

    @Test
    public void sendCodeThroughSuccessTest(){
        GetCode getCode = resetPasswordFactory.sendCodeThrough("Email");
        Boolean value = getCode.getClass().isInstance(new SendMailForForgotPassword());
        Assertions.assertEquals(true , value);
    }
}
