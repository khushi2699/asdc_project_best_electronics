package com.best.electronics.forgotPassword;

public class InvalidPasswordFormatForgotPasswordState extends ForgotPasswordState{

    InvalidPasswordFormatForgotPasswordState() {
        super();
    }

    @Override
    public void setStatus() {
        status = "Invalid password format";

    }
}
