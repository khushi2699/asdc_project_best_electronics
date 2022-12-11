package com.best.electronics.database;

import com.best.electronics.login.EncryptPassword;

public class ProductToCartPersistence {

    private static ProductToCartPersistence productToCartPersistence = null;

    public static ProductToCartPersistence getInstance(){
        if(productToCartPersistence == null){
            productToCartPersistence = new ProductToCartPersistence();
        }
        return productToCartPersistence;
    }

    public void addProductsToCart(){

    }
}
