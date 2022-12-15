package com.best.electronics.forgotPassword;

public class InvalidPasswordFormatAuthHandler extends ForgotPasswordEmailHandler{

    private final IInvalidPasswordFormat iInvalidPasswordFormat;

    public InvalidPasswordFormatAuthHandler(IInvalidPasswordFormat iInvalidPasswordFormat)  {
        this.iInvalidPasswordFormat = iInvalidPasswordFormat;
    }

    @Override
    public ForgotPasswordState doHandler(String password, String confirmPassword, String email) throws Exception {
        if(iInvalidPasswordFormat.isValidPassword(password)){
            return nextHandler(password,confirmPassword,email);
        } else {
            return new InvalidPasswordFormatForgotPasswordState();
        }
    }
}
