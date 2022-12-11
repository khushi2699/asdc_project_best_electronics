package com.best.electronics.login;

import com.best.electronics.database.AdminMockDatabasePersistence;
import com.best.electronics.database.IDatabasePersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminLoginHandlerTest {

    private static ILoginValidationHandler loginValidationHandler;

    @BeforeAll
    public static void init() throws Exception {
        IDatabasePersistence mockDatabasePersistence = new AdminMockDatabasePersistence();
        loginValidationHandler = new GenericLoginValidationHandler
                ("{call get_admin_login_details()}", mockDatabasePersistence);
    }

    @Test
    public void loginSuccessTest() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("admin@gmail.com", "Newuser@123", "admin");
        Assertions.assertEquals(loginState.getNextPage(), "adminLandingPage");
        Assertions.assertEquals(loginState.getLoginStatus(), "Successfully logged in");
    }

    @Test
    public void loginFailTestWrongEmail() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("g@gmail.com", "Newuser@123", "admin");
        Assertions.assertEquals(loginState.getNextPage(), "adminLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "EmailAddress does not Exists!");
    }

    @Test
    public void loginFailTestWrongPassword() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("admin@gmail.com", "Newuser@125", "admin");
        Assertions.assertEquals(loginState.getNextPage(), "adminLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "Password is incorrect!");
    }
}
