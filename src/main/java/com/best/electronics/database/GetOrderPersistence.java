package com.best.electronics.database;

import java.util.ArrayList;
import java.util.Map;

public class GetOrderPersistence {
    private static GetOrderPersistence getOrderPersistence = null;

    public static GetOrderPersistence getInstance(){
        if(getOrderPersistence == null){
            getOrderPersistence = new GetOrderPersistence();
        }
        return getOrderPersistence;
    }

    public Integer getOrderId(int id) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        result = databasePersistence.loadData("{call getOrderId(?)}", tokenDetails);
        return (Integer) result.get(0).get("orderId");
    }
}
