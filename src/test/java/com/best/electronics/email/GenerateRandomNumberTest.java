package com.best.electronics.email;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenerateRandomNumberTest {
    static GenerateRandomNumber generateRandomNumber;

    @BeforeAll
    public static void init(){
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
