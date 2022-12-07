package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginHandler implements ILoginHandler {

    @Override
    public LoginState login(String emailAddress, String password, HttpServletRequest request) {
        LoginState loginState = null;
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("Select emailAddress,password from Admin", databasePersistence);
            AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

            loginState = authHandler.doHandler(emailAddress, password, "admin");
            if(loginState.getNextPage().equals("adminLandingPage.html")){
                SessionManager sessionManager = new SessionManager();
                sessionManager.getSession(request);
                return loginState;
            }
            return loginState;
        }catch(Exception e){
            System.out.println("Exception happened in Admin login");
            return loginState;
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.invalidateSession(request);
    }

    @Override
    public Boolean resetPassword(String emailAddress, String newPassword) {
        return null;
    }
}
