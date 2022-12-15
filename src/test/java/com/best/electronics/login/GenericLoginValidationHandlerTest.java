package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
import com.best.electronics.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenericLoginValidationHandlerTest {

    private static GenericLoginValidationHandler genericLoginValidationHandler;

    @BeforeAll
    public static void init() throws Exception {
        IDatabasePersistence db = new UserMockDatabasePersistence();
        genericLoginValidationHandler = new GenericLoginValidationHandler("{call get_user_login_details()}", db);
    }

    @Test
    public void isValidEmailAddressSuccessTest() {
        Boolean value = genericLoginValidationHandler.isValidEmailAddress("p@gmail.com");
        Assertions.assertEquals(true, value);
    }

    @Test
    public void isValidEmailAddressFailTest(){
        Boolean value = genericLoginValidationHandler.isValidEmailAddress("g@gmail.com");
        Assertions.assertEquals(false, value);
    }

    @Test
    public void isValidPasswordSuccessTest(){
        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@123");
        Boolean value = genericLoginValidationHandler.isValidPassword(user);
        Assertions.assertEquals(true, value);
    }

    @Test
    public void isValidPasswordFailTestWithWrongEmail(){
        User user = new User();
        user.setEmailAddress("g@gmail.com");
        user.setPassword("Newuser@123");
        Boolean value = genericLoginValidationHandler.isValidPassword(user);
        Assertions.assertEquals(false, value);
    }

    @Test
    public void isValidPasswordFailTestWithWrongPassword(){
        User user = new User();
        user.setEmailAddress("p@gmail.com");
        user.setPassword("Newuser@124");
        Boolean value = genericLoginValidationHandler.isValidPassword(user);
        Assertions.assertEquals(false, value);
    }
}
