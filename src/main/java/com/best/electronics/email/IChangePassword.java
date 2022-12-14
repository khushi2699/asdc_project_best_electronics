package com.best.electronics.email;

import com.best.electronics.forgotPassword.ForgotPasswordState;

public interface IChangePassword {

    public ForgotPasswordState storeNewPassword(String password, String confirmPassword, String email, String type) throws Exception;

}
