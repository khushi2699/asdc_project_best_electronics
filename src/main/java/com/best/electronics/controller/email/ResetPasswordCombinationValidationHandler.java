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
    public String checkCombination(int token, String email) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        ArrayList<Map<String, Object>> result= new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        try {
            result = databasePersistence.loadData("{call (?, ?)}");
            if(result.size() == 0){
                return "Please enter correct credentails";
            }
            else {
                return "Combinations correct";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

