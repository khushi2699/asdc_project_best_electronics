package com.best.electronics.login;

import com.best.electronics.model.Account;

public interface ILoginValidationHandler {

    Boolean isValidEmailAddress(String emailAddress);

    Boolean isValidPassword(Account account);
}
