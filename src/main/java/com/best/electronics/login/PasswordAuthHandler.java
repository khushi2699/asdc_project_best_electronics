package com.best.electronics.login;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.login.IncorrectPasswordState;

public class PasswordAuthHandler extends LoginAuthHandler {

    private final ILoginValidationHandler loginValidation;

    public PasswordAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;

    }

    @Override
    public State doHandler(Account account, String type) throws Exception {
        if(loginValidation.isValidPassword(account)){
            return nextHandler(account, type);
        }else{
            return new IncorrectPasswordState(type);
        }
    }
}
