package com.best.electronics.email;

import com.best.electronics.forgotPassword.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChangePasswordHandlerTest {

    ChangePasswordHandler changePasswordHandler;

    @Before
    public void init(){
        changePasswordHandler = new ChangePasswordHandler();
    }

    @Test
    public void storeNewPasswordSuccessTest() throws Exception {
        String password = "Admin@1234";
        String confirmPassword = "Admin@1234";
        String email = "admin@gmail.com";

        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(iInvalidPasswordFormat));
        ForgotPasswordState forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);

        String result = forgotPasswordState.getStatus();
        Assertions.assertEquals(result, "Password changed");
    }

    @Test
    public void storeNewPasswordFailureInvalidFormatTest() throws Exception {
        String password = "Admin";
        String confirmPassword = "Admin";
        String email = "admin@gmail.com";
        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(iInvalidPasswordFormat));
        ForgotPasswordState forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);

        String result = forgotPasswordState.getStatus();
        Assertions.assertEquals(result, "Invalid password format");
    }

    @Test
    public void storeNewPasswordFailurePasswordMisMatchTest() throws Exception {
        String password = "Admin@1234";
        String confirmPassword = "Admin";
        String email = "admin@gmail.com";
        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(iInvalidPasswordFormat));
        ForgotPasswordState forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);

        String result = forgotPasswordState.getStatus();
        Assertions.assertEquals(result, "Password and Confirm password do not match");
    }

}
