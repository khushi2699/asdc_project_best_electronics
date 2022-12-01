package com.best.electronics.database;


import com.best.electronics.authentication.ILoginValidationHandler;
import com.best.electronics.authentication.UserLoginValidationHandler;
import com.best.electronics.authentication.ValidationService;
import com.best.electronics.session.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class UserLoginHandler implements ILoginHandler {

    @Override
    public Boolean login(String emailAddress, String password, HttpServletRequest request) {
        try{
            ILoginValidationHandler loginValidationHandler = new UserLoginValidationHandler
                    ("Select emailAddress,password from User");
            ValidationService validationService = new ValidationService(loginValidationHandler);

            if(validationService.validate(emailAddress, password)){
                SessionManager sessionManager = new SessionManager();
                sessionManager.getSession(request);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Exception happened in User login");
            return false;
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
