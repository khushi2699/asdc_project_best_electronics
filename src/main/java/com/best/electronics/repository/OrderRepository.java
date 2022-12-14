package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Order;

import java.util.ArrayList;
import java.util.Map;

public class OrderRepository {
    private final IDatabasePersistence databasePersistence;

    public OrderRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public void saveOrderItems(ArrayList<Map<String, Object>> cartListResult, int orderId){
        for (Map<String, Object> stringObjectMap : cartListResult) {
            ArrayList<Object> tokenDetails = new ArrayList<>();
            tokenDetails.add(stringObjectMap.get("quantity"));
            tokenDetails.add(orderId);
            tokenDetails.add(stringObjectMap.get("productId"));
            tokenDetails.add(stringObjectMap.get("productPrice"));
            int quantity = (int) stringObjectMap.get("quantity");
            double productPrice = (double) stringObjectMap.get("productPrice");
            tokenDetails.add(quantity * productPrice);
            try {
                if (databasePersistence.saveData("{call saveOrderItem(?,?,?,?,?)}", tokenDetails)) {
                    System.out.println("Done");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void placeOrder(Order order){
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
