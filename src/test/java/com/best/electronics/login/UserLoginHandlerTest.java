package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserLoginHandlerTest {

    private static ILoginValidationHandler loginValidationHandler;

    @BeforeAll
    public static void init() throws Exception {
        IDatabasePersistence mockDatabasePersistence = new UserMockDatabasePersistence();
        loginValidationHandler = new GenericLoginValidationHandler
                ("{call get_user_login_details()}", mockDatabasePersistence);
    }

    @Test
    public void loginSuccessTest() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("p@gmail.com", "Newuser@123", "user");
        Assertions.assertEquals(loginState.getNextPage(), "productList.html");
        Assertions.assertEquals(loginState.getLoginStatus(), "Successfully logged in");
    }

    @Test
    public void loginFailTestWrongEmail() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("g@gmail.com", "Newuser@123", "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin.html");
        Assertions.assertEquals(loginState.getLoginStatus(), "EmailAddress does not Exists!");
    }

    @Test
    public void loginFailTestWrongPassword() throws Exception {
        AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        LoginState loginState = authHandler.doHandler("p@gmail.com", "Newuser@125", "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin.html");
        Assertions.assertEquals(loginState.getLoginStatus(), "Password is incorrect!");
    }
}
