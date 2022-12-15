package com.best.electronics.login;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;
import com.best.electronics.state.login.InvalidEmailAddressState;

public class EmailAuthHandler extends LoginAuthHandler {

    private final ILoginValidationHandler loginValidation;

    public EmailAuthHandler(ILoginValidationHandler loginValidation){
        this.loginValidation = loginValidation;
    }

    @Override
    public State doHandler(Account account, String type) throws Exception {
        if(loginValidation.isValidEmailAddress(account.getEmailAddress())){
            return nextHandler(account, type);
        }else{
            return new InvalidEmailAddressState(type);
        }
    }
}
