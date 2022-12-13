package com.best.electronics.register;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.security.EncryptPassword;
import com.best.electronics.model.Account;
import com.best.electronics.repository.UserRepository;
import com.best.electronics.state.State;
import com.best.electronics.state.register.GenericFailedRegisterState;

import java.util.ArrayList;
import java.util.Map;

public class UserRegisterHandler implements IRegisterHandler{

    public State register(Account account, Map<String, Object> typeSpecificParameters) {
        State registerState = new GenericFailedRegisterState("user");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_user_emailAddress()}", databasePersistence);
            registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

            registerState = registerHandler.validate(account, "user");

            if(registerState.getNextPage().equals("userRegisterSuccess")){
                ArrayList<Object> userDetails = new ArrayList<>();
                userDetails.add(account.getFirstName());
                userDetails.add(account.getLastName());
                userDetails.add(account.getEmailAddress());
                userDetails.add(EncryptPassword.getInstance().encryptString(account.getPassword()));
                userDetails.add(typeSpecificParameters.get("dob"));
                userDetails.add(account.getGender());
                userDetails.add(typeSpecificParameters.get("address"));
                UserRepository userRepository = new UserRepository(databasePersistence);
                if(userRepository.saveUserData(userDetails)){
                    return registerState;
                }
                return new GenericFailedRegisterState("user");
            }
        } catch(Exception e){
            System.out.println("Exception occurred in Register Handler.");
            return registerState;
        }
        return registerState;
    }
}
