package com.best.electronics.forgotPassword;

public abstract class ForgotPasswordState {
    String status;
    ForgotPasswordState(){
        setStatus();
    }

    public String getStatus() {
        return status;
    }

    public abstract void setStatus();
}
