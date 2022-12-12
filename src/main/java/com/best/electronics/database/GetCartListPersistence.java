package com.best.electronics.database;

import java.util.ArrayList;
import java.util.Map;

public class GetCartListPersistence {

    private static GetCartListPersistence getCartListPersistence = null;

    public static GetCartListPersistence getInstance(){
        if(getCartListPersistence == null){
            getCartListPersistence = new GetCartListPersistence();
        }
        return getCartListPersistence;
    }

    public ArrayList<Map<String, Object>> getCartListDetails(int id) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        result = databasePersistence.loadData("{call getCartDetails(?)}", tokenDetails);
        return result;
    }
}
