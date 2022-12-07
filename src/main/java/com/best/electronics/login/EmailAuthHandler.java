package com.best.electronics.login;

public class EmailAuthHandler extends AuthHandler {

    private final ILoginValidationHandler loginValidation;

    public EmailAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;
    }

    @Override
    public LoginState doHandler(String emailAddress, String password, String type) throws Exception {
        if(loginValidation.isValidEmailAddress(emailAddress)){
            return nextHandler(emailAddress, password, type);
        }else{
            return new InvalidEmailAddressState(type);
        }
    }
}
