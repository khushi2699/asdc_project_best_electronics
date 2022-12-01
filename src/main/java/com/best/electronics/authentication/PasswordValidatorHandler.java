package com.best.electronics.authentication;

public class PasswordValidatorHandler extends AuthHandler{

    private final ILoginValidationHandler loginValidation;

    public PasswordValidatorHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;

    }

    @Override
    public boolean doHandler(String emailAddress, String password) throws Exception {
        if(loginValidation.isValidPassword(emailAddress, password)){
            return nextHandler(emailAddress, password);
        }else{
            return false;
        }
    }
}
