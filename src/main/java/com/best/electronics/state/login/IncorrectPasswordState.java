package com.best.electronics.state.login;

import com.best.electronics.state.State;

public class IncorrectPasswordState extends State {

    public IncorrectPasswordState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Password is incorrect!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "Login";
    }

}
