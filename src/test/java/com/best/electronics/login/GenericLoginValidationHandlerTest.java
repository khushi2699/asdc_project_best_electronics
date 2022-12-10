package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.UserMockDatabasePersistence;
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
        Boolean value = genericLoginValidationHandler.isValidPassword("p@gmail.com", "Newuser@123");
        Assertions.assertEquals(true, value);
    }

    @Test
    public void isValidPasswordFailTestWithWrongEmail(){
        Boolean value = genericLoginValidationHandler.isValidPassword("g@gmail.com", "Newuser@123");
        Assertions.assertEquals(false, value);
    }

    @Test
    public void isValidPasswordFailTestWithWrongPassword(){
        Boolean value = genericLoginValidationHandler.isValidPassword("p@gmail.com", "Newuser@124");
        Assertions.assertEquals(false, value);
    }
}
