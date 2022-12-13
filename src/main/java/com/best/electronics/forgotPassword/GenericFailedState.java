package com.best.electronics.forgotPassword;

public class GenericFailedState extends ForgotPasswordState{

    public GenericFailedState(){
        super();
    }
    @Override
    public void setStatus() {
        status = "Something unexpected happened";
    }
}
