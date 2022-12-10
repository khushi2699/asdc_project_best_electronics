package com.best.electronics.register;

public class RegisterSuccessState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Registered Successfully";
    }

    @Override
    public void setNextPage() {
        nextPage = "registerSuccess.html";
    }
}