package com.best.electronics.login;

public interface ILoginValidationHandler {

    Boolean isValidEmailAddress(String emailAddress);

    Boolean isValidPassword(String emailAddress, String password);
}
