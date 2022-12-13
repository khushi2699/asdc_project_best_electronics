package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class PasswordRepository {

    private final IDatabasePersistence databasePersistence;

    public PasswordRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public boolean saveNewpassword(String password, String email) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(password);
        tokenDetails.add(email);
        if(databasePersistence.saveData("{call save_new_password(?, ?)}", tokenDetails)){
            return true;
        } else {
            return false;
        }
    }

    public boolean storePinToDB(Integer token, String email) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);

        if(databasePersistence.saveData("{call save_forgot_password_token(?, ?)}", tokenDetails)){
            return true;
        }
        else {
            return false;
        }
    }
    public ArrayList<Map<String, Object>> checkCombination(Integer token, String email) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        ArrayList<Map<String, Object>> result = databasePersistence.loadData("{call get_check_combinations(?, ?)}",tokenDetails);
        return result;
    }

    public ArrayList<Map<String, Object>> getEmailCheck(String email) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(email);
        ArrayList<Map<String,Object>> result = databasePersistence.loadData("{call get_email_check(?)}",tokenDetails);
        return result;
    }
}
