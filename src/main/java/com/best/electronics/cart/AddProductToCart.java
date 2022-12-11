package com.best.electronics.cart;

import com.best.electronics.database.ProductToCartPersistence;

public class AddProductToCart {

    private static AddProductToCart addProductToCart = null;

    public static AddProductToCart getInstance(){
        if(addProductToCart == null){
            addProductToCart = new AddProductToCart();
        }
        return addProductToCart;
    }

    public void saveProductToCart(Integer productId, Integer quantity){
        ProductToCartPersistence productToCartPersistence = ProductToCartPersistence.getInstance();

    }
}
