package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public class AdminLoginHandler implements ILoginHandler {

    @Override
    public LoginState login(String emailAddress, String password, HttpServletRequest request) {
        LoginState loginState = null;
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ILoginValidationHandler loginValidationHandler = new GenericLoginValidationHandler
                    ("{call get_admin_login_details()}", databasePersistence);
            AuthHandler authHandler = new EmailAuthHandler(loginValidationHandler);
            authHandler.setNextHandler(new PasswordAuthHandler(loginValidationHandler));

            loginState = authHandler.doHandler(emailAddress, password, "admin");
            if(loginState.getNextPage().equals("adminLandingPage")){
                SessionManager sessionManager = new SessionManager();
                HttpSession session = sessionManager.getSession(request);

                ArrayList<Object> parameters = new ArrayList<>();
                parameters.add(emailAddress);
                ArrayList<Map<String, Object>> adminDetails = databasePersistence.loadData("{call get_admin_details_based_on_email(?)}", parameters);
                Map<String, Object> adminDetail = adminDetails.get(0);
                session.setAttribute("adminId", adminDetail.get("adminId"));
                session.setAttribute("adminName", adminDetail.get("firstName"));
                session.setAttribute("adminEmailAddress", emailAddress);

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

}
