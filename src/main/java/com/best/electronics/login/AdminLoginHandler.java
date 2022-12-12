package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.Account;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginHandler implements ILoginHandler {

    @Override
    public LoginState login(Account admin, HttpServletRequest request) {
        LoginState loginState = new GenericFailedLoginState("admin");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("{call get_admin_login_details()}", databasePersistence);
            LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler))
                    .setNextHandler(new SessionCreationHandler(request));

            loginState = authHandler.doHandler(admin, "admin");
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

}
