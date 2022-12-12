package com.best.electronics.register;

public class EmailAlreadyExistsState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Email address already Exists!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm";
    }
}
