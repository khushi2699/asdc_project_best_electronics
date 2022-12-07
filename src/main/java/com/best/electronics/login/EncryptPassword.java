package com.best.electronics.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {

    private static EncryptPassword encryptPassword = null;

    public static EncryptPassword getInstance(){
        if(encryptPassword == null){
            encryptPassword = new EncryptPassword();
        }
        return encryptPassword;
    }

    public String encryptString(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] plainText = password.getBytes();
        md.reset();
        md.update(plainText);
        byte[] cipher = md.digest();

        StringBuilder builder = new StringBuilder();
        for(byte c: cipher){
            if((c & 0xff) < 0x10){
                builder.append(1);
            }
            builder.append(Long.toString(c & 0xff, 16));
        }
        return builder.toString();
    }
}
