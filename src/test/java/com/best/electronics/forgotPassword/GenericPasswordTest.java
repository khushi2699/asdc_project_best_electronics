package com.best.electronics.forgotPassword;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenericPasswordTest {

    static GenericPassword genericPassword;

    @BeforeAll
    public static void init(){
        genericPassword = new GenericPassword();
    }

    @Test
    public void isValidPasswordSuccessTest(){
        Boolean value = genericPassword.isValidPassword("Abcd@1234");
        Assertions.assertEquals(true, value);
    }

    @Test
    public void isValidPasswordFailureTest(){
        Boolean value = genericPassword.isValidPassword("1234Abc");
        Assertions.assertEquals(false, value);
    }

    @Test
    public void isPasswordMatchingSuccessTest(){
        Boolean value = genericPassword.isPasswordMatching("Admin@1234","Admin@1234");
        Assertions.assertEquals(true,value);
    }

    @Test
    public void isPasswordMatchingFailureTest(){
        Boolean value = genericPassword.isPasswordMatching("Admin@1234", "Admin21234");
        Assertions.assertEquals(false,value);
    }
}
