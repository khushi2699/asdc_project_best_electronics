package com.best.electronics.login;

import javax.servlet.http.HttpServletRequest;

public interface ILoginHandler {

    LoginState login(String emailAddress, String password, HttpServletRequest request);

    void logout(HttpServletRequest request);
}
