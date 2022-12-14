package com.best.electronics.cartandwishlist;

import java.util.ArrayList;
import java.util.Map;

public class GetTotalOfProduct {

    private static GetTotalOfProduct getTotalOfProduct = null;

    public static GetTotalOfProduct getInstance(){
        if(getTotalOfProduct == null){
            getTotalOfProduct = new GetTotalOfProduct();
        }
        return getTotalOfProduct;
    }

    public double calculateTotalOfProducts(ArrayList<Map<String, Object>> cartListResult){
        double totalSum = 0;
        for (Map<String, Object> stringObjectMap : cartListResult) {
            int quantity = (int) stringObjectMap.get("quantity");
            System.out.println("Quantity " + quantity);
            double productPrice = (double) stringObjectMap.get("productPrice");
            double productSum = quantity * productPrice;
            totalSum = totalSum + productSum;
        }
        return totalSum;
    }
}
