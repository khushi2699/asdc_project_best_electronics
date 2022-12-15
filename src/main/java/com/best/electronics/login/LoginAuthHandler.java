package com.best.electronics.login;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.login.SuccessLoginState;

public abstract class LoginAuthHandler{
    private LoginAuthHandler nextHandler;

    public LoginAuthHandler setNextHandler(LoginAuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    public abstract State doHandler(Account account, String type) throws Exception;

    protected State nextHandler(Account account, String type) throws Exception {
        if(nextHandler == null){
            return new SuccessLoginState(type);
        }
        return nextHandler.doHandler(account, type);
    }
}
