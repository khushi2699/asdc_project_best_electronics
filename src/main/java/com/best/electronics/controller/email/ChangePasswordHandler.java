package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.register.MissMatchPasswordState;

import java.util.ArrayList;

public class ChangePasswordHandler implements EmailControllerPinResetStore{
    @Override
    public void storePinToDB(int token, String email) {

    }

    @Override
    public boolean checkCombination(int token, String email) {
        return false;
    }

    @Override
    public void storeNewPassword(String password, String confirmPassword, String email) {
        MissMatchPassword missMatchPassword = new MissMatchPassword();
        String passwordValidation = missMatchPassword.checkMissMatch(password, confirmPassword);
        System.out.println("Password Validation "+ passwordValidation);


        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(password);
        tokenDetails.add(email);
        try {
            if(!databasePersistence.saveData("{call save_new_password(?, ?)}", tokenDetails)){
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
