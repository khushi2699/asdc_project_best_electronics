package com.best.electronics.forgotPassword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericPassword implements IInvalidPasswordFormat{

    @Override
    public Boolean isValidPassword(String password) {
        String urlPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_?])[A-Za-z\\d!@#$%^&*_?]{8,}$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    @Override
    public Boolean isPasswordMatching(String password, String confirmPassword) {
        if(confirmPassword.equalsIgnoreCase(password))
        {
            return true;
        } else {
            return false;
        }

    }
}
