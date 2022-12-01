package com.best.electronics.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

    public void getSession(HttpServletRequest request){
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        //generate a new session
        HttpSession newSession = request.getSession(true);
//        newSession.setAttribute("isUser", true);
//        System.out.println(newSession.getAttribute("isUser"));
    }

    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
