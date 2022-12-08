package com.best.electronics.database.email;

import com.best.electronics.controller.email.EmailControllerPinResetStoreDAO;

public class EmailResetPinQueryBuilder implements EmailResetPinQueryBuilderInterface{
    private static EmailResetPinQueryBuilder instance;

    @Override
    public String saveEmailResetPinQuery(int randomNumber) {
        return "UPDATE `CSCI5308_4_DEVINT`.`User` SET `resetPasswordToken` = '"+randomNumber+"' WHERE (`userId` = '1');";
    }
}
