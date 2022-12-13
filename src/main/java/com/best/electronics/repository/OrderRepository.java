package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.Order;

import java.util.ArrayList;
import java.util.Map;

public class OrderRepository {
    private final IDatabasePersistence databasePersistence;

    public OrderRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
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
