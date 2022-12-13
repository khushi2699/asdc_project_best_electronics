package com.best.electronics.forgotPassword;

public class InvalidPasswordFormatAuthHandler extends ForgotPasswordEmailHandler{

    private String password;
    private String confirmPassword;
    private String email;
    private final IInvalidPasswordFormat iInvalidPasswordFormat;

    public InvalidPasswordFormatAuthHandler(String password, String confirmPassword, String email,IInvalidPasswordFormat iInvalidPasswordFormat)  {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
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
