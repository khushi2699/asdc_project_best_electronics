package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class EmailAlreadyExistsState extends State {

    public EmailAlreadyExistsState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Email address already Exists!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegistrationForm";
    }

}
