package com.best.electronics.register;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.register.InvalidPasswordState;
import com.best.electronics.state.register.MissMatchPasswordState;
import com.best.electronics.state.register.RegisterSuccessState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordRegisterValidation extends RegisterAuthHandler {

    @Override
    public State validate(Account account, String type) {
        String password = account.getPassword();
        String reEnteredPassword = account.getConfirmPassword();

        if (isPasswordValid(password)) {
            if (reEnteredPassword.equals(password)) {
                return new RegisterSuccessState(type);
            } else {
                return new MissMatchPasswordState(type);
            }
        } else {
            return new InvalidPasswordState(type);
        }
    }

    private Boolean isPasswordValid(String password) {
        String urlPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*_?])[A-Za-z\\d!@#$%^&*_?]{8,}$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}

