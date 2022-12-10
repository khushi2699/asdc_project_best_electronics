package com.best.electronics.controller.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissMatchPassword {

    public String checkMissMatch(String password, String confirmPassword){
        if(isPasswordValid(password)){
            if(confirmPassword.equals(password)){
                return "OK";
            }else{
                return "Password and Confirm password does not match";
            }
        }else{
            return "Invalid password pattern";
        }
    }

    private Boolean isPasswordValid(String password) {
        String urlPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_?])[A-Za-z\\d!@#$%^&*_?]{8,}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
