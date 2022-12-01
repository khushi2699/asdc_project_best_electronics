package com.best.electronics.authentication;

public interface ILoginValidationHandler {

    Boolean isValidEmailAddress(String emailAddress);

    Boolean isValidPassword(String emailAddress, String password);
}
