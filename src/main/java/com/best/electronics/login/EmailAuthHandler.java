package com.best.electronics.login;

import com.best.electronics.model.Account;

public class EmailAuthHandler extends LoginAuthHandler {

    private final ILoginValidationHandler loginValidation;

    public EmailAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;
    }

    @Override
    public LoginState doHandler(Account account, String type) throws Exception {
        if(loginValidation.isValidEmailAddress(account.getEmailAddress())){
            return nextHandler(account, type);
        }else{
            return new InvalidEmailAddressState(type);
        }
    }
}
