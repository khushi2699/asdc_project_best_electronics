package com.best.electronics.cartandwishlist;

import com.best.electronics.login.EncryptPassword;

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
        double totalsum = 0;
        for(int i = 0 ; i < cartListResult.size(); i++){
            int quantity = (int) cartListResult.get(i).get("quantity");
            System.out.println("Quantity " + quantity);
            double productPrice = (double) cartListResult.get(i).get("productPrice");
            double productsum = quantity * productPrice;
            totalsum = totalsum + productsum;
        }
        return totalsum;
    }
}
