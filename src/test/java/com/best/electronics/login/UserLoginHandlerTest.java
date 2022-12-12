package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import com.best.electronics.model.User;
import com.best.electronics.session.SessionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserLoginHandlerTest {

    private static ILoginValidationHandler loginValidationHandler;

    HttpServletRequest servletRequest;

    @BeforeEach
    public void init() throws Exception {
        IDatabasePersistence mockDatabasePersistence = new UserMockDatabasePersistence();
        loginValidationHandler = new GenericLoginValidationHandler
                ("{call get_user_login_details()}", mockDatabasePersistence);
        servletRequest = new MockHttpServletRequest();
    }

    @Test
    public void loginSuccessTest() throws Exception {
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

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
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

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
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@125");
        LoginState loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "Password is incorrect!");
    }

    @Test
    public void loginFailTestWhenSessionIsNull() throws Exception {
        servletRequest = mock(HttpServletRequest.class);
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@123");
        LoginState loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getLoginStatus(), "Unexpected exception occurred! Please try again!");
    }
}
