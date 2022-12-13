package com.best.electronics.forgotPassword;

public class PasswordMissMatchForgotPasswordState extends ForgotPasswordState{

    PasswordMissMatchForgotPasswordState() {
        super();
    }

    @Override
    public void setStatus() {
        status = "Password and Confirm password do not match";

    }
}
