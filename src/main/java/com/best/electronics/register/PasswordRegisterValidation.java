package com.best.electronics.register;

import com.best.electronics.model.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordRegisterValidation extends RegisterAuthHandler {

    @Override
    public RegisterState validate(Account account) {
        String password = account.getPassword();
        String reEnteredPassword = account.getConfirmPassword();

        if (isPasswordValid(password)) {
            if (reEnteredPassword.equals(password)) {
                return new RegisterSuccessState();
            } else {
                return new MissMatchPasswordState();
            }
        } else {
            return new InvalidPasswordState();
        }
    }

    private Boolean isPasswordValid(String password) {
        String urlPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*_?])[A-Za-z\\d!@#$%^&*_?]{8,}$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}

