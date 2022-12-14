package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class InvalidPasswordState extends State {

    public InvalidPasswordState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Password must contain at least one uppercase, lowercase, number and special character. Min 8 length!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegistrationForm";
    }
}
