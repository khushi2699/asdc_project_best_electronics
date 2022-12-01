package com.best.electronics.database;

import javax.servlet.http.HttpServletRequest;

public interface ILoginHandler {

    Boolean login(String emailAddress, String password, HttpServletRequest request);

    void logout(HttpServletRequest request);

    Boolean resetPassword(String emailAddress, String newPassword);
}
