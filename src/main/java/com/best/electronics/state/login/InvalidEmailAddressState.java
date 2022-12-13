package com.best.electronics.state.login;

import com.best.electronics.state.State;

public class InvalidEmailAddressState extends State {

    public InvalidEmailAddressState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "EmailAddress does not Exists!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "Login";
    }
}
