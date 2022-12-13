package com.best.electronics.database;

import java.util.ArrayList;
import java.util.Map;

public class GetUserPaymentDetailPersistence {

    private static GetUserPaymentDetailPersistence getUserPaymentDetailPersistence = null;

    public static GetUserPaymentDetailPersistence getInstance(){
        if(getUserPaymentDetailPersistence == null){
            getUserPaymentDetailPersistence = new GetUserPaymentDetailPersistence();
        }
        return getUserPaymentDetailPersistence;
    }

    public ArrayList<Map<String, Object>> getCardDetails(int userId) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(userId);
        result = databasePersistence.loadData("{call getCardDetails(?)}", tokenDetails);
        return result;
    }
}
