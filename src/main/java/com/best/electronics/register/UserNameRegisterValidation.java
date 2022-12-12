package com.best.electronics.register;

import com.best.electronics.model.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameRegisterValidation extends RegisterAuthHandler{

    @Override
    public RegisterState validate(Account account) {
        String firstName = account.getFirstName();
        String lastName = account.getLastName();

        if(isUsernameValid(firstName) && isUsernameValid(lastName)){
            return nextHandler(account);
        }

        return new InvalidUsernameState();
    }

    private Boolean isUsernameValid(String name) {
        String urlPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
