package com.best.electronics.register;

public class InvalidPasswordState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Password must contain at least one uppercase, lowercase, number and special character. Min 8 length!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm";
    }
}
