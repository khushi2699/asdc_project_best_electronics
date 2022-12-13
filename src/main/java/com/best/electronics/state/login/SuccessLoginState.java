package com.best.electronics.state.login;

import com.best.electronics.state.State;

public class SuccessLoginState extends State {

    public SuccessLoginState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Successfully logged in";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "LandingPage";
    }

}
