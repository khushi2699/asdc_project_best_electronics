package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public class UserLoginHandler implements ILoginHandler {

    @Override
    public LoginState login(String emailAddress, String password, HttpServletRequest request) {
        LoginState loginState = new GenericFailedLoginState("user");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("{call get_user_login_details()}", databasePersistence);
            AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

            loginState = authHandler.doHandler(emailAddress, password, "user");

            if(loginState.getNextPage().equals("redirect:/products")){
                SessionManager sessionManager = new SessionManager();
                HttpSession session = sessionManager.getSession(request);

                ArrayList<Object> parameters = new ArrayList<>();
                parameters.add(emailAddress);
                ArrayList<Map<String, Object>> userDetails = databasePersistence.loadData("{call get_user_details_based_on_email(?)}", parameters);
                Map<String, Object> userDetail = userDetails.get(0);
                session.setAttribute("userId", userDetail.get("userId"));
                session.setAttribute("userName", userDetail.get("firstName"));

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
