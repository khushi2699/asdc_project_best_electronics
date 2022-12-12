package com.best.electronics.login;

public class IncorrectPasswordState extends LoginState{

    IncorrectPasswordState(String type) {
        super(type);
    }

    @Override
    public void setLoginStatus() {
        loginStatus = "Password is incorrect!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "Login";
    }

}
