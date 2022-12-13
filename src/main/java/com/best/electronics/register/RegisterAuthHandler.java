package com.best.electronics.register;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.register.RegisterSuccessState;

public abstract class RegisterAuthHandler {

    RegisterAuthHandler nextHandler;

    public RegisterAuthHandler setNextHandler(RegisterAuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public abstract State validate(Account account, String type);

    protected State nextHandler(Account account, String type) {
        if(nextHandler == null){
            return new RegisterSuccessState(type);
        }
        return nextHandler.validate(account, type);
    }
}
