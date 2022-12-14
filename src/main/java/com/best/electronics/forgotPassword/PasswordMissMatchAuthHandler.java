package com.best.electronics.forgotPassword;

public class PasswordMissMatchAuthHandler extends ForgotPasswordEmailHandler{
    private final IInvalidPasswordFormat iInvalidPasswordFormat;


    public PasswordMissMatchAuthHandler(IInvalidPasswordFormat iInvalidPasswordFormat){
        this.iInvalidPasswordFormat = iInvalidPasswordFormat;
    }

    @Override
    public ForgotPasswordState doHandler(String password, String confirmPassword, String email) throws Exception {
        if(iInvalidPasswordFormat.isPasswordMatching(password,confirmPassword)){
            return nextHandler(password,confirmPassword,email);
        }
        else {
            return new PasswordMissMatchForgotPasswordState();
        }
    }
}
