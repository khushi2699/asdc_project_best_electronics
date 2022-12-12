package com.best.electronics.login;

import com.best.electronics.model.Account;

public class PasswordAuthHandler extends LoginAuthHandler {

    private final ILoginValidationHandler loginValidation;

    public PasswordAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;

    }

    @Override
    public LoginState doHandler(Account account, String type) throws Exception {
        if(loginValidation.isValidPassword(account)){
            return nextHandler(account, type);
        }else{
            return new IncorrectPasswordState(type);
        }
    }
}
