package com.best.electronics.login;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Account;
import com.best.electronics.security.EncryptPassword;

import java.util.ArrayList;
import java.util.Map;

public class GenericLoginValidationHandler implements ILoginValidationHandler{

    private final ArrayList<Map<String, Object>> data;

    public GenericLoginValidationHandler(String query, IDatabasePersistence databasePersistence) throws Exception {
        data = databasePersistence.loadData(query, new ArrayList<>());
    }

    @Override
    public Boolean isValidEmailAddress(String emailAddress) {
        if(data != null && data.size() > 0){
            for(Map<String, Object> d: data){
                if(emailAddress.equals(d.get("emailAddress").toString())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isValidPassword(Account account) {
        if(data.size() > 0){
            String emailAddress = account.getEmailAddress();
            String password = account.getPassword();
            for(Map<String, Object> d: data){
                if(emailAddress.equals(d.get("emailAddress"))){
                    String dbPassword = (String) d.get("password");
                    try{
                        String encryptedPassword = EncryptPassword.getInstance().encryptString(password);
                        if(encryptedPassword.equals(dbPassword)){
                            account.setAccountId((Integer) d.get("id"));
                            account.setFirstName((String) d.get("firstName"));
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