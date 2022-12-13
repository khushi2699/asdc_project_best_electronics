package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class MissMatchPasswordState extends State {

    public MissMatchPasswordState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "The re-entered password and password are not matching!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegistrationForm";
    }
}
