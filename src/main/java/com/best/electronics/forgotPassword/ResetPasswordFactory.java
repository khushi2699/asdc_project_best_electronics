package com.best.electronics.forgotPassword;

import com.best.electronics.email.SendMailForForgotPassword;

public class ResetPasswordFactory {

    public GetCode sendCodeThrough(String medium){
        if(medium == null || medium.isEmpty()){
            return null;
        }
        switch (medium){
            case "Email":
                return new SendMailForForgotPassword();
                default:
                    throw new IllegalArgumentException("Unknown medium "+ medium);

        }
    }

}
