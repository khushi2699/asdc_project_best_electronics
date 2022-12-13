package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.forgotPassword.*;
import com.best.electronics.login.EncryptPassword;
import com.best.electronics.repository.PasswordRepository;

import java.util.ArrayList;

public class ChangePasswordHandler implements IChangePassword {

    @Override
    public ForgotPasswordState storeNewPassword(String password, String confirmPassword, String email) throws Exception {

        ForgotPasswordState forgotPasswordState = new GenericFailedState();
        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(password, confirmPassword, email, iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(password, confirmPassword, email, iInvalidPasswordFormat));
        forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);
        System.out.println(forgotPasswordState.getStatus());

        if (forgotPasswordState.getStatus().equalsIgnoreCase("Done")) {
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            password = EncryptPassword.getInstance().encryptString(password);

            PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
            passwordRepository.saveNewpassword(password, email);
            return forgotPasswordState;

        }
        return forgotPasswordState;
    }
}