package com.best.electronics.authentication;

public class EmailValidatorHandler extends AuthHandler{

    private final ILoginValidationHandler loginValidation;

    public EmailValidatorHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;

    }

    @Override
    public boolean doHandler(String emailAddress, String password) throws Exception {
        if(loginValidation.isValidEmailAddress(emailAddress)){
            return nextHandler(emailAddress, password);
        }else{
            return false;
        }
    }
}
