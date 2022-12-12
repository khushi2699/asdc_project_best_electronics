package com.best.electronics.register;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Account;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRegisterValidation extends RegisterAuthHandler{

    private final ArrayList<Map<String, Object>> data;

    public EmailRegisterValidation(String query, IDatabasePersistence databasePersistence) throws Exception {
        data = databasePersistence.loadData(query, new ArrayList<>());
    }

    @Override
    public RegisterState validate(Account account) {
        String emailAddress = account.getEmailAddress();

        if(isEmailAddressValid(emailAddress) && isNewEmailAddress(emailAddress)){
            return nextHandler(account);
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
        if (!data.isEmpty()) {
            for (Map<String, Object> userData : data) {
                String dbEmailAddress = (String) userData.get("emailAddress");
                if (dbEmailAddress.equalsIgnoreCase(emailAddress)) {
                    return false;
                }
            }
        }
        return true;
    }
}
