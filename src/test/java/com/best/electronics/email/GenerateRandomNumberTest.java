package com.best.electronics.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenerateRandomNumberTest {
    GenerateRandomNumber generateRandomNumber;

    @Before
    public void init(){
        generateRandomNumber = new GenerateRandomNumber();
    }

    @Test
    public void generateRandomNumberSuccessTest(){
        int randomNumber = generateRandomNumber.generateRandomNumber();
        int randomNumber1 = 123456;
        Assertions.assertEquals(((Object)randomNumber1).getClass() , ((Object) randomNumber).getClass());
    }

    @Test
    public void generateRandomNumberFailureTest(){
        int randomNumber = generateRandomNumber.generateRandomNumber();
        String randomNumber1 = "123456";
        Assertions.assertNotEquals(((Object)randomNumber1).getClass() , ((Object) randomNumber).getClass());
    }
}
