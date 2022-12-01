package com.best.electronics.authentication;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class AdminLoginValidationHandler implements ILoginValidationHandler{

    private final ArrayList<Map<String, Object>> adminsData;

    public AdminLoginValidationHandler(String query) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        adminsData = databasePersistence.loadData(query);
    }

    @Override
    public Boolean isValidEmailAddress(String emailAddress) {
        if(adminsData.size() > 0){
            for(Map<String, Object> adminData: adminsData){
                if(emailAddress.equals(adminData.get("emailAddress").toString())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isValidPassword(String emailAddress, String password) {
        if (adminsData.size() > 0) {
            for (Map<String, Object> adminData : adminsData) {
                if (emailAddress.equals(adminData.get("emailAddress"))) {
                    String dbPassword = (String) adminData.get("password");
                    try {
                        String encryptedPassword = EncryptPassword.getInstance().encryptString(password);
                        if (encryptedPassword.equals(dbPassword)) {
                            return true;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

}

