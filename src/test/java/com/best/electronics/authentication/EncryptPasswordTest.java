package com.best.electronics.authentication;

import com.best.electronics.login.EncryptPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class EncryptPasswordTest {

    private static EncryptPassword encryptPassword;

    @BeforeAll
    public static void init(){
        encryptPassword = EncryptPassword.getInstance();
    }

    @Test
    public void successEncryptPasswordTest() throws NoSuchAlgorithmException {
        String password = "password";
        String encryptedPassword = "5f4dcc3b5aa765d61d8327deb882cf99";

        Assertions.assertEquals(encryptedPassword, encryptPassword.encryptString(password));
    }

    @Test
    public void failEncryptPasswordTest() throws NoSuchAlgorithmException {
        String password = "password";
        String encryptedPassword = "5f4dcc3b5aa765d61d8327deb882cf77";

        Assertions.assertNotEquals(encryptedPassword, encryptPassword.encryptString(password));
    }
}
