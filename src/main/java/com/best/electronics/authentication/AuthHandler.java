package com.best.electronics.authentication;

public abstract class AuthHandler{
    private AuthHandler nextHandler;

    public AuthHandler setNextHandler(AuthHandler nextHandler){
        this.nextHandler = nextHandler;
        return nextHandler;
    }
    public abstract boolean doHandler(String emailAddress, String password) throws Exception;

    protected boolean nextHandler(String emailAddress, String password) throws Exception {
        if(nextHandler == null){
            return true;
        }
        return nextHandler.doHandler(emailAddress, password);
    }
}
