package com.best.electronics.forgotPassword;
public interface IInvalidPasswordFormat {
    Boolean isValidPassword(String password);

    Boolean isPasswordMatching(String password, String confirmPassword);

}
