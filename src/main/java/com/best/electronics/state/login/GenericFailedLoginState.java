package com.best.electronics.state.login;

import com.best.electronics.state.State;

public class GenericFailedLoginState extends State {

    public GenericFailedLoginState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Unexpected exception occurred! Please try again!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "Login";
    }
}
