package com.best.electronics.login;

import com.best.electronics.model.Account;
import com.best.electronics.state.State;

import javax.servlet.http.HttpServletRequest;

public interface ILoginHandler {

    State login(Account account, HttpServletRequest request);

    void logout(HttpServletRequest request);
}
