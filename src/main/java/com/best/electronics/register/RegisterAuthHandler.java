package com.best.electronics.register;

import com.best.electronics.model.Account;

public abstract class RegisterAuthHandler {

    RegisterAuthHandler nextHandler;

    public RegisterAuthHandler setNextHandler(RegisterAuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public abstract RegisterState validate(Account account);

    protected RegisterState nextHandler(Account account) {
        if(nextHandler == null){
            return new RegisterSuccessState();
        }
        return nextHandler.validate(account);
    }
}
