package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class InvalidUsernameState extends State {

    public InvalidUsernameState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Either firstName or lastName are not in correct format!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegistrationForm";
    }
}
