package com.best.electronics.authentication;

public class ValidationService {

    private final ILoginValidationHandler loginValidationHandler;

    public ValidationService(ILoginValidationHandler loginValidationHandler){
        this.loginValidationHandler = loginValidationHandler;
    }

    public Boolean validate(String emailAddress, String password){
        AuthHandler authHandler = new EmailValidatorHandler(loginValidationHandler);
        authHandler.setNextHandler(new PasswordValidatorHandler(loginValidationHandler));

        try{
            if(authHandler.doHandler(emailAddress, password)){
                return true;
            }
        }catch(Exception e){
            System.out.println("Exception occurred!");
            return false;
        }
        return false;
    }
}
