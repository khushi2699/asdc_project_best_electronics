package com.best.electronics.login;

public abstract class LoginState {

    String loginStatus;

    String nextPage;

    LoginState(String type){
        setLoginStatus();
        setNextPage(type);
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public abstract void setLoginStatus();

    public String getNextPage() {
        return nextPage;
    }

    public abstract void setNextPage(String type);
}
