package com.best.electronics.register;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRegisterValidation extends RegisterAuthHandler{

    private final ArrayList<Map<String, Object>> usersData;

    public EmailRegisterValidation(IDatabasePersistence databasePersistence) throws Exception {
        usersData = databasePersistence.loadData("{call get_user_emailAddress()}");
    }

    @Override
    public RegisterState validate(User user) {
        String emailAddress = user.getEmailAddress();

        if(isEmailAddressValid(emailAddress) && isNewEmailAddress(emailAddress)){
            return nextHandler(user);
        }
        return new EmailAlreadyExistsState();
    }

    private Boolean isEmailAddressValid(String emailAddress) {
        String urlPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.find();
    }

    private Boolean isNewEmailAddress(String emailAddress) {
        if (!usersData.isEmpty()) {
            for (Map<String, Object> userData : usersData) {
                String dbEmailAddress = (String) userData.get("emailAddress");
                if (dbEmailAddress.equalsIgnoreCase(emailAddress)) {
                    return false;
                }
            }
        }
        return true;
    }
}
