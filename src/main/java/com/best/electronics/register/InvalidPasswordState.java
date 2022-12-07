package com.best.electronics.register;

public class InvalidPasswordState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Password is not entered in correct format!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm.html";
    }
}
