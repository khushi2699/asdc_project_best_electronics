package com.best.electronics.login;

import com.best.electronics.model.Account;
import com.best.electronics.session.SessionManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCreationHandler extends LoginAuthHandler{

    private final HttpServletRequest request;

    public SessionCreationHandler(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public LoginState doHandler(Account account, String type){
        SessionManager sessionManager = new SessionManager();
        HttpSession session = sessionManager.getSession(request);

        if(session == null){
            return new GenericFailedLoginState(type);
        }

        session.setAttribute("id", account.getAccountId());
        session.setAttribute("emailAddress", account.getEmailAddress());
        session.setAttribute("name", account.getFirstName());
        return new SuccessLoginState(type);
    }
}
