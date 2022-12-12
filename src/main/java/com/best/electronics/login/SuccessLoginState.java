package com.best.electronics.login;

public class SuccessLoginState extends LoginState{

    SuccessLoginState(String type) {
        super(type);
    }

    @Override
    public void setLoginStatus() {
        loginStatus = "Successfully logged in";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "LandingPage";
    }

}
