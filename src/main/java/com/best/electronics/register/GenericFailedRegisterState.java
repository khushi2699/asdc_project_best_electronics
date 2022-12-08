package com.best.electronics.register;

public class GenericFailedRegisterState extends RegisterState{
    @Override
    public void setRegisterStatus() {
        registerStatus = "Unexpected exception occurred! Please try again!";
    }

    @Override
    public void setNextPage() {
        nextPage = "registrationForm.html";
    }
}
