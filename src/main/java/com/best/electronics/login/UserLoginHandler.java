package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class UserLoginHandler implements ILoginHandler {

    @Override
    public LoginState login(String emailAddress, String password, HttpServletRequest request) {
        LoginState loginState = new GenericFailedLoginState("user");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("Select emailAddress,password from User", databasePersistence);
            AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

            loginState = authHandler.doHandler(emailAddress, password, "user");

            if(loginState.getNextPage().equals("productList.html")){
                SessionManager sessionManager = new SessionManager();
                sessionManager.getSession(request);
                return loginState;
            }
            return loginState;
        }catch(Exception e){
            System.out.println("Exception happened in User login");
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
