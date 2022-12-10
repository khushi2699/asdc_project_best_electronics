package com.best.electronics.controller.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.EncryptPassword;
import com.best.electronics.register.MissMatchPasswordState;

import java.security.NoSuchAlgorithmException;
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
    public String storeNewPassword(String password, String confirmPassword, String email) throws NoSuchAlgorithmException {
        MissMatchPassword missMatchPassword = new MissMatchPassword();
        String passwordValidation = missMatchPassword.checkMissMatch(password, confirmPassword);

        if (passwordValidation.equalsIgnoreCase("OK")) {
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            ArrayList<Object> tokenDetails = new ArrayList<>();
            password = EncryptPassword.getInstance().encryptString(password);
            tokenDetails.add(password);
            tokenDetails.add(email);
            System.out.println(password);
            System.out.println(email);
            try {
                if (databasePersistence.saveData("{call save_new_password(?, ?)}", tokenDetails)) {
                    return "Password changed";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (passwordValidation.equalsIgnoreCase("Password and Confirm password does not match")) {
            return "No match";
        } else if (passwordValidation.equalsIgnoreCase("Invalid password pattern")) {
            return "Invalid pattern";
        } else {
            return null;
        }
        return null;
    }
}
