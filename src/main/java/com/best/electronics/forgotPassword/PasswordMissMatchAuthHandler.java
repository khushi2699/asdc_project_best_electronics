package com.best.electronics.forgotPassword;

public class PasswordMissMatchAuthHandler extends ForgotPasswordEmailHandler{

    private String password;
    private String confirmPassword;
    private String email;

    private final IInvalidPasswordFormat iInvalidPasswordFormat;


    public PasswordMissMatchAuthHandler(String password, String confirmPassword, String email, IInvalidPasswordFormat iInvalidPasswordFormat){
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
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
