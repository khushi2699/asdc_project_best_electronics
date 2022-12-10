package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.register.GenericFailedRegisterState;

import java.util.ArrayList;

public class EmailControllerPinStoreHandler implements EmailControllerPinResetStore {

    @Override
    public void storePinToDB(int token, String email) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(token);
        tokenDetails.add(email);
        try {
            if(!databasePersistence.saveData("{call save_forgot_password_token(?, ?)}", tokenDetails)){
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkCombination(int token, String email) {
        return true;
    }

    @Override
    public String storeNewPassword(String password, String confirmPassword, String email) {
        return null;
    }
}

