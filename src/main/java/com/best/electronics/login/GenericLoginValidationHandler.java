package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class GenericLoginValidationHandler implements ILoginValidationHandler{

    private final ArrayList<Map<String, Object>> data;

    public GenericLoginValidationHandler(String query, IDatabasePersistence databasePersistence) throws Exception {
        data = databasePersistence.loadData(query);
    }

    @Override
    public Boolean isValidEmailAddress(String emailAddress) {
        if(data != null && data.size() > 0){
            for(Map<String, Object> userData: data){
                if(emailAddress.equals(userData.get("emailAddress").toString())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isValidPassword(String emailAddress, String password) {
        if(data.size() > 0){
            for(Map<String, Object> userData: data){
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