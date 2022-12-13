package com.best.electronics.database;

import com.best.electronics.model.CardDetails;

import java.util.ArrayList;

public class SaveCardDetailsPersistence {

    private static SaveCardDetailsPersistence saveCardDetailsPersistence = null;

    public static SaveCardDetailsPersistence getInstance(){
        if(saveCardDetailsPersistence == null){
            saveCardDetailsPersistence = new SaveCardDetailsPersistence();
        }
        return saveCardDetailsPersistence;
    }

    public void saveCard(CardDetails cardDetails){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cardDetails.getCardName());
        tokenDetails.add(cardDetails.getSecurityCode());
        tokenDetails.add(cardDetails.getExpiryDate());
        tokenDetails.add(cardDetails.getUserId());
        tokenDetails.add(cardDetails.getCardType());
        tokenDetails.add(cardDetails.getCardNumber());
        try {
            if (databasePersistence.saveData("{call savecardDetails(?,?,?,?,?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
