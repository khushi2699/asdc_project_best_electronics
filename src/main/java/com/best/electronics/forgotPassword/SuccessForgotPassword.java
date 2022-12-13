package com.best.electronics.forgotPassword;

public class SuccessForgotPassword extends ForgotPasswordState{

    public SuccessForgotPassword(){
        super();
    }
    @Override
    public void setStatus() {
        status = "Password changed";
    }

    @Override
    public void setNextPage() {
        nextPage = "redirect:/user/login";

    }

}
