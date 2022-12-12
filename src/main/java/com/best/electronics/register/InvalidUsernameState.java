package com.best.electronics.register;

public class InvalidUsernameState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Either firstName or lastName are not in correct format!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm";
    }
}
