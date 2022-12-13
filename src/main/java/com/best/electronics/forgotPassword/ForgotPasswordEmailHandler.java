package com.best.electronics.forgotPassword;

import com.best.electronics.login.SuccessLoginState;

public abstract class ForgotPasswordEmailHandler {
    private ForgotPasswordEmailHandler nextHandler;

    public ForgotPasswordEmailHandler setNextHandler(ForgotPasswordEmailHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    public abstract ForgotPasswordState doHandler(String password, String confirmPassword, String email) throws Exception;

    protected ForgotPasswordState nextHandler(String password, String confirmPassword, String email) throws Exception {
        if(nextHandler == null){
            return new SuccessForgotPassword();
        }
        return nextHandler.doHandler(password,confirmPassword,email);
    }
}
