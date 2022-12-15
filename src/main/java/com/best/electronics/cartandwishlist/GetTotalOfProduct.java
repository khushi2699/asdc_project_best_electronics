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

    //below function will calculate total of the products
    public double calculateTotalOfProducts(ArrayList<Map<String, Object>> cartListResult){
        double totalsum = 0;
        for (Map<String, Object> stringObjectMap : cartListResult) {
            int quantity = (int) stringObjectMap.get("quantity");
            double productPrice = (double) stringObjectMap.get("productPrice");
            double productsum = quantity * productPrice;
            totalsum = totalsum + productsum;
        }
        return totalsum;
    }
}
