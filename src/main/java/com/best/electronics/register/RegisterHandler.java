package com.best.electronics.register;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.login.EncryptPassword;
import com.best.electronics.model.User;
import com.best.electronics.repository.UserRepository;

import java.util.ArrayList;

public class RegisterHandler {

    public RegisterState register(User user) {
        RegisterState registerState = new GenericFailedRegisterState();
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_user_emailAddress()}", databasePersistence);
            registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

            registerState = registerHandler.validate(user);

            if(registerState.getNextPage().equals("registerSuccess")){
                ArrayList<Object> userDetails = new ArrayList<>();
                userDetails.add(user.getFirstName());
                userDetails.add(user.getLastName());
                userDetails.add(user.getEmailAddress());
                userDetails.add(EncryptPassword.getInstance().encryptString(user.getPassword()));
                userDetails.add(user.getDateOfBirth());
                userDetails.add(user.getGender());
                userDetails.add(user.getAddress());
                UserRepository userRepository = new UserRepository(databasePersistence);
                userRepository.saveUserData(userDetails);
            }
        } catch(Exception e){
            System.out.println("Exception occurred in Register Handler.");
            return registerState;
        }
        return registerState;
    }
}
