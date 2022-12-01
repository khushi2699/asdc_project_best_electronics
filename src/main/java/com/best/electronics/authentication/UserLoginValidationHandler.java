package com.best.electronics.authentication;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class UserLoginValidationHandler implements ILoginValidationHandler{

    private final ArrayList<Map<String, Object>> usersData;

    public UserLoginValidationHandler(String query) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        usersData = databasePersistence.loadData(query);
    }

    @Override
    public Boolean isValidEmailAddress(String emailAddress) {
        if(usersData.size() > 0){
            for(Map<String, Object> userData: usersData){
                if(emailAddress.equals(userData.get("emailAddress").toString())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isValidPassword(String emailAddress, String password) {
        if(usersData.size() > 0){
            for(Map<String, Object> userData: usersData){
                if(emailAddress.equals(userData.get("emailAddress"))){
                    String dbPassword = (String) userData.get("password");
                    try{
                        String encryptedPassword = EncryptPassword.getInstance().encryptString(password);
                        if(encryptedPassword.equals(dbPassword)){
                            return true;
                        }
                    }catch(Exception e){
                        return false;
                    }
                }
            }
        }
        return false;
    }
}