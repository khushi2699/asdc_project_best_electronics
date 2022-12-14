package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class PasswordRepository {

    private final IDatabasePersistence databasePersistence;

    public PasswordRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public boolean saveNewPassword(String password, String email) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(password);
        tokenDetails.add(email);
        return databasePersistence.saveData("{call save_new_password(?, ?)}", tokenDetails);
    }

    public boolean storePinToDB(Integer token, String email) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        return databasePersistence.saveData("{call save_forgot_password_token(?, ?)}", tokenDetails);
    }
    public ArrayList<Map<String, Object>> checkCombination(Integer token, String email) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        return databasePersistence.loadData("{call get_check_combinations(?, ?)}",tokenDetails);
    }

    public ArrayList<Map<String, Object>> getEmailCheck(String email) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(email);
        return databasePersistence.loadData("{call get_email_check(?)}",tokenDetails);
    }
}
