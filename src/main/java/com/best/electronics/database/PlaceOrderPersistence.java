package com.best.electronics.database;

import com.best.electronics.model.Order;

import java.util.ArrayList;
import java.util.Map;

public class PlaceOrderPersistence {
    private static PlaceOrderPersistence placeOrderPersistence = null;

    public static PlaceOrderPersistence getInstance(){
        if(placeOrderPersistence == null){
            placeOrderPersistence = new PlaceOrderPersistence();
        }
        return placeOrderPersistence;
    }

    public void placeorder(Order order){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(order.getUserId());
        tokenDetails.add(order.getOrderAmount());
        tokenDetails.add(order.getOrderStatus());
        tokenDetails.add(order.getPaymentMethod());
        tokenDetails.add(order.getAddress());
        tokenDetails.add(order.getOrderDate());
        try {
            if (databasePersistence.saveData("{call save_to_order_details(?,?,?,?,?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
