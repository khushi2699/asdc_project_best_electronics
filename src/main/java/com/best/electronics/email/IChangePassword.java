package com.best.electronics.email;

import com.best.electronics.forgotPassword.ForgotPasswordState;

public interface IChangePassword {
    ForgotPasswordState storeNewPassword(String password, String confirmPassword, String email) throws Exception;
}
