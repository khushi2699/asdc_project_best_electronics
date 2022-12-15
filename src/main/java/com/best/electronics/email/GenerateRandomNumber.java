package com.best.electronics.email;

import java.util.Random;

public class GenerateRandomNumber {
    private static GenerateRandomNumber generateRandomNumber = null;

    public static GenerateRandomNumber getInstance(){
        if(generateRandomNumber == null){
            generateRandomNumber = new GenerateRandomNumber();
        }
        return generateRandomNumber;
    }

    public int generateRandomNumber(){
        Random randomNumber = new Random();
        return randomNumber.nextInt(999999);
    }
}
