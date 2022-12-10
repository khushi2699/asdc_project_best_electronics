package com.best.electronics.register;

import com.best.electronics.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameRegisterValidation extends RegisterAuthHandler{

    @Override
    public RegisterState validate(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        if(isUsernameValid(firstName) && isUsernameValid(lastName)){
            return nextHandler(user);
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
