package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import com.best.electronics.model.User;
import com.best.electronics.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

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
        State loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLandingPage");
        Assertions.assertEquals(loginState.getStatus(), "Successfully logged in");
    }

    @Test
    public void loginFailTestWrongEmail() throws Exception {
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

        User user = new User();
        user.setEmailAddress("g@gmail.com");
        user.setPassword("Newuser@123");
        State loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getStatus(), "EmailAddress does not Exists!");
    }

    @Test
    public void loginFailTestWrongPassword() throws Exception {
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@125");
        State loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getStatus(), "Password is incorrect!");
    }

    @Test
    public void loginFailTestWhenSessionIsNull() throws Exception {
        servletRequest = mock(HttpServletRequest.class);
        LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler)).setNextHandler(new SessionCreationHandler(servletRequest));

        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@123");
        State loginState = authHandler.doHandler(user, "user");
        Assertions.assertEquals(loginState.getNextPage(), "userLogin");
        Assertions.assertEquals(loginState.getStatus(), "Unexpected exception occurred! Please try again!");
    }
}
