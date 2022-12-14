package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.email.EmailControllerPinResetStore;
import com.best.electronics.repository.PasswordRepository;

import java.util.ArrayList;

public class EmailControllerPinStoreHandler implements EmailControllerPinResetStore {

    @Override
    public void storePinToDB(Integer token, String email) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
        try {
            passwordRepository.storePinToDB(token,email);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

