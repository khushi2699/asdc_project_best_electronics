package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.forgotPassword.*;
import com.best.electronics.repository.PasswordRepository;
import com.best.electronics.security.EncryptPassword;

public class ChangePasswordHandler implements IChangePassword {

    @Override
    public ForgotPasswordState storeNewPassword(String password, String confirmPassword, String email, String type) throws Exception {

        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(iInvalidPasswordFormat));
        ForgotPasswordState forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);

        if (forgotPasswordState.getStatus().equalsIgnoreCase("Password changed")) {
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            password = EncryptPassword.getInstance().encryptString(password);
            PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
            passwordRepository.saveNewpassword(password, email, type);
            return forgotPasswordState;

        }
        return forgotPasswordState;
    }
}