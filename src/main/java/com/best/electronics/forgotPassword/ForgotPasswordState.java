package com.best.electronics.forgotPassword;

public abstract class ForgotPasswordState {
    String status;

    String nextPage;
    ForgotPasswordState(){
        setNextPage();
        setStatus();
    }

    public String getStatus() {
        return status;
    }

    public abstract void setStatus();

    public String getNextPage() {
        return nextPage;
    }
    public abstract void setNextPage();
}
