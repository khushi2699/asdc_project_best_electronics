package com.best.electronics.login;

public class GenericFailedLoginState extends LoginState{

    GenericFailedLoginState(String type) {
        super(type);
    }

    @Override
    public void setLoginStatus() {
        loginStatus = "Unexpected exception occurred! Please try again!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "Login";
    }
}
