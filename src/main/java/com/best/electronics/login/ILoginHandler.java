package com.best.electronics.login;

import com.best.electronics.model.Account;

import javax.servlet.http.HttpServletRequest;

public interface ILoginHandler {

    LoginState login(Account account, HttpServletRequest request);

    void logout(HttpServletRequest request);
}
