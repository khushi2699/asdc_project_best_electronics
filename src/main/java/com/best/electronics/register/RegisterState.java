package com.best.electronics.register;

public abstract class RegisterState {

    String registerStatus;

    String nextPage;

    RegisterState(){
        setRegisterStatus();
        setNextPage();
    }

    public String getLoginStatus() {
        return registerStatus;
    }

    public abstract void setRegisterStatus();

    public String getNextPage() {
        return nextPage;
    }

    public abstract void setNextPage();
}
