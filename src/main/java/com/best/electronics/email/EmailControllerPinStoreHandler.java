package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.repository.PasswordRepository;

public class EmailControllerPinStoreHandler implements EmailControllerPinResetStore {

    @Override
    public boolean storePinToDB(Integer token, String email,String type) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
        try {
            return passwordRepository.storePinToDB(token, email, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

