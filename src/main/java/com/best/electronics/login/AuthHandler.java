package com.best.electronics.login;

public abstract class AuthHandler{
    private AuthHandler nextHandler;

    public void setNextHandler(AuthHandler nextHandler){
        this.nextHandler = nextHandler;
    }
    public abstract LoginState doHandler(String emailAddress, String password, String type) throws Exception;

    protected LoginState nextHandler(String emailAddress, String password, String type) throws Exception {
        if(nextHandler == null){
            return new SuccessLoginState(type);
        }
        return nextHandler.doHandler(emailAddress, password, type);
    }
}
