package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.email.ICheckCombination;
import com.best.electronics.repository.PasswordRepository;

import java.util.ArrayList;
import java.util.Map;

public class ResetPasswordCombinationValidationHandler implements ICheckCombination {

    @Override
    public boolean checkCombination(Integer token, String email, String type) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
        try {
            ArrayList<Map<String, Object>> result = passwordRepository.checkCombination(token,email,type);
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
}

