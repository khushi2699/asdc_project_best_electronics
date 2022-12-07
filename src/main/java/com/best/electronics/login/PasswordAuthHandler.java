package com.best.electronics.login;

public class PasswordAuthHandler extends AuthHandler {

    private final ILoginValidationHandler loginValidation;

    public PasswordAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;

    }

    @Override
    public LoginState doHandler(String emailAddress, String password, String type){
        if(loginValidation.isValidPassword(emailAddress, password)){
            return new SuccessLoginState(type);
        }else{
            return new IncorrectPasswordState(type);
        }
    }
}
