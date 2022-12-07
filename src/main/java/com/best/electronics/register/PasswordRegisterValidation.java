package com.best.electronics.register;

import com.best.electronics.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordRegisterValidation extends RegisterAuthHandler{

    @Override
    public RegisterState validate(User user) {
        String password = user.getPassword();
        String reEnteredPassword = user.getReEnterPassword();

        if(isPasswordValid(password)){
            if(reEnteredPassword.equals(password)){
                return new RegisterSuccessState();
            }else{
                return new MissMatchPasswordState();
            }
        }else{
            return new InvalidPasswordState();
        }
    }

    private Boolean isPasswordValid(String password) {
        String urlPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_?])[A-Za-z\\d!@#$%^&*_?]{8,}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
