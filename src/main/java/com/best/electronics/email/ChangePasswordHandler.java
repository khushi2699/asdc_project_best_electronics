package com.best.electronics.email;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.forgotPassword.*;
import com.best.electronics.login.EncryptPassword;
import com.best.electronics.repository.PasswordRepository;

import java.util.ArrayList;

public class ChangePasswordHandler implements IChangePassword {

    @Override
    public String storeNewPassword(String password, String confirmPassword, String email) throws Exception {

        ForgotPasswordState forgotPasswordState = new GenericFailedState();
        IInvalidPasswordFormat iInvalidPasswordFormat = new GenericPassword();
        ForgotPasswordEmailHandler forgotPasswordEmailHandler = new InvalidPasswordFormatAuthHandler(password, confirmPassword, email,iInvalidPasswordFormat);
        forgotPasswordEmailHandler.setNextHandler(new PasswordMissMatchAuthHandler(password,confirmPassword,email,iInvalidPasswordFormat));
        forgotPasswordState = forgotPasswordEmailHandler.doHandler(password, confirmPassword, email);
        System.out.println(forgotPasswordState.getStatus());

        if(forgotPasswordState.getStatus().equalsIgnoreCase("Done")){
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            password = EncryptPassword.getInstance().encryptString(password);

            PasswordRepository passwordRepository = new PasswordRepository(databasePersistence);
            try {
                if(passwordRepository.saveNewpassword(password,email)){
                    return "Password changed";
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(forgotPasswordState.getStatus().equalsIgnoreCase("Password and Confirm password do not match")){
            System.out.println("inside Password and Confirm password do not match");
            return "No match";

        }
        else if(forgotPasswordState.getStatus().equalsIgnoreCase("Invalid password format")){
            System.out.println("inside Invalid password format");
            return "Invalid pattern";
        }
        else {
            return null;
        }
        return null;
    }
}
