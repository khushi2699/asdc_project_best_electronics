package com.best.electronics.login;

import com.best.electronics.model.Account;

public abstract class LoginAuthHandler{
    private LoginAuthHandler nextHandler;

    public LoginAuthHandler setNextHandler(LoginAuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    public abstract LoginState doHandler(Account account, String type) throws Exception;

    protected LoginState nextHandler(Account account, String type) throws Exception {
        if(nextHandler == null){
            return new SuccessLoginState(type);
        }
        return nextHandler.doHandler(account, type);
    }
}
