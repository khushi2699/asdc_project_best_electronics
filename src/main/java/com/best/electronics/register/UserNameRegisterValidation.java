package com.best.electronics.register;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.register.InvalidUsernameState;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameRegisterValidation extends RegisterAuthHandler{

    @Override
    public State validate(Account account, String type) {
        String firstName = account.getFirstName();
        String lastName = account.getLastName();

        if(isUsernameValid(firstName) && isUsernameValid(lastName)){
            return nextHandler(account, type);
        }

        return new InvalidUsernameState(type);
    }

    private Boolean isUsernameValid(String name) {
        String urlPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
