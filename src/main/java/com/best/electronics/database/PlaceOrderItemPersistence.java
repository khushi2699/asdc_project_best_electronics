package com.best.electronics.database;

import java.util.ArrayList;
import java.util.Map;

public class PlaceOrderItemPersistence {

    private static PlaceOrderItemPersistence placeOrderItemPersistence = null;

    public static PlaceOrderItemPersistence getInstance(){
        if(placeOrderItemPersistence == null){
            placeOrderItemPersistence = new PlaceOrderItemPersistence();
        }
        return placeOrderItemPersistence;
    }

    public void saveOrderItems(ArrayList<Map<String, Object>> cartListResult, int orderId){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        for(int i = 0 ; i < cartListResult.size(); i++){
            ArrayList<Object> tokenDetails = new ArrayList<>();
            tokenDetails.add(cartListResult.get(i).get("quantity"));
            tokenDetails.add(orderId);
            tokenDetails.add(cartListResult.get(i).get("productId"));
            tokenDetails.add(cartListResult.get(i).get("productPrice"));
            int quantity = (int) cartListResult.get(i).get("quantity");
            double productPrice = (double) cartListResult.get(i).get("productPrice");
            tokenDetails.add(quantity*productPrice);
            try {
                if (databasePersistence.saveData("{call saveOrderItem(?,?,?,?,?)}", tokenDetails)) {
                    System.out.println("Done");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
