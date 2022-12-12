package com.best.electronics.register;

public class MissMatchPasswordState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "The re-entered password and password are not matching!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm";
    }
}
