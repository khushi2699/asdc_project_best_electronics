package com.best.electronics.email;

public interface IChangePassword {

    public String storeNewPassword(String password, String confirmPassword, String email) throws Exception;

}
