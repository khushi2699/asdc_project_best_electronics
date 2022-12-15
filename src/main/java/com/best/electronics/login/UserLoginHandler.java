package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.Account;
import com.best.electronics.session.SessionManager;
import com.best.electronics.state.State;
import com.best.electronics.state.login.GenericFailedLoginState;

import javax.servlet.http.HttpServletRequest;

public class UserLoginHandler implements ILoginHandler {

    @Override
    public State login(Account user, HttpServletRequest request) {
        State loginState = new GenericFailedLoginState("user");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("{call get_user_login_details()}", databasePersistence);
            LoginAuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler))
                    .setNextHandler(new SessionCreationHandler(request));

            loginState = authHandler.doHandler(user, "user");
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
}
