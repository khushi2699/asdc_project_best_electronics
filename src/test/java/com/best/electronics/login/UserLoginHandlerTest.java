package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import com.best.electronics.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
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
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@123");
        LoginState loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLandingPage");
        Assertions.assertEquals(loginState.getLoginStatus(), "Successfully logged in");
    }

    @Test
    public void loginFailTestWrongEmail() throws Exception {
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        User user = new User();
        user.setEmailAddress("g@gmail.com");
        user.setPassword("Newuser@123");
        LoginState loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "EmailAddress does not Exists!");
    }

    @Test
    public void loginFailTestWrongPassword() throws Exception {
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@125");
        LoginState loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "Password is incorrect!");
    }
}
