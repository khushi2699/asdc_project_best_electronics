package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class ResetPasswordCombinationValidationHandler implements EmailControllerPinResetStore{
    @Override
    public void storePinToDB(int token, String email) {

    }

    @Override
    public boolean checkCombination(int token, String email) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        ArrayList<Map<String, Object>> result= new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        try {
            result = databasePersistence.loadData("{call get_check_combinations(?, ?)}",tokenDetails);
            if(result.size() == 0){
                return false;
            }
            else {
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String storeNewPassword(String password,String confirmPassword, String email) {
        return null;
    }
}

