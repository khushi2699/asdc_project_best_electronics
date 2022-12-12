package com.best.electronics.database;

import com.best.electronics.model.CartItem;

import java.util.ArrayList;

public class ProductToCartPersistence {

    private static ProductToCartPersistence productToCartPersistence = null;

    public static ProductToCartPersistence getInstance(){
        if(productToCartPersistence == null){
            productToCartPersistence = new ProductToCartPersistence();
        }
        return productToCartPersistence;
    }

    public void addProductsToCart(CartItem cartItem){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cartItem.getUserId());
        tokenDetails.add(cartItem.getCartItemId());
        tokenDetails.add(cartItem.getQuantity());

        try {
            if (databasePersistence.saveData("{call save_product_to_cart(?,?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
