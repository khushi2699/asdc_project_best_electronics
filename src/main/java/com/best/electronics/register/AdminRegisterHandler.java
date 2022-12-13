package com.best.electronics.register;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.Account;
import com.best.electronics.repository.AdminRepository;
import com.best.electronics.security.EncryptPassword;
import com.best.electronics.state.State;
import com.best.electronics.state.register.GenericFailedRegisterState;

import java.util.ArrayList;
import java.util.Map;

public class AdminRegisterHandler implements IRegisterHandler{

    @Override
    public State register(Account account, Map<String, Object> typeSpecificParameters) {
        State registerState = new GenericFailedRegisterState("admin");
        try{
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            RegisterAuthHandler registerHandler = new EmailRegisterValidation("{call get_admin_emailAddress()}", databasePersistence);
            registerHandler.setNextHandler(new UserNameRegisterValidation()).setNextHandler(new PasswordRegisterValidation());

            registerState = registerHandler.validate(account, "admin");

            if(registerState.getNextPage().equals("adminRegisterSuccess")){
                ArrayList<Object> adminDetails = new ArrayList<>();
                adminDetails.add(account.getFirstName());
                adminDetails.add(account.getLastName());
                adminDetails.add(account.getEmailAddress());
                adminDetails.add(EncryptPassword.getInstance().encryptString(account.getPassword()));
                adminDetails.add(account.getGender());
                AdminRepository adminRepository = new AdminRepository(databasePersistence);
                if(adminRepository.saveAdminData(adminDetails)){
                    return registerState;
                }
                return new GenericFailedRegisterState("admin");
            }
        } catch(Exception e){
            System.out.println("Exception occurred in Amin Register Handler.");
            return registerState;
        }
        return registerState;
    }
}
