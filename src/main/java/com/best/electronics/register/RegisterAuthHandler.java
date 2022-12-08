package com.best.electronics.register;

import com.best.electronics.model.User;

public abstract class RegisterAuthHandler {

    RegisterAuthHandler nextHandler;

    public RegisterAuthHandler setNextHandler(RegisterAuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public abstract RegisterState validate(User user);

    protected RegisterState nextHandler(User user) {
        if(nextHandler == null){
            return new RegisterSuccessState();
        }
        return nextHandler.validate(user);
    }
}
