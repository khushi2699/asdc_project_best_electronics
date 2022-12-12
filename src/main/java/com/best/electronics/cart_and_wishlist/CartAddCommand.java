package com.best.electronics.cart_and_wishlist;

import com.best.electronics.database.ProductToCartPersistence;
import com.best.electronics.model.CartItem;
public class CartAddCommand implements CommandForProduct{

    CartItem cartItem;

    public CartAddCommand(CartItem cartItem){
        this.cartItem = cartItem;
    }

    @Override
    public void execute() {
        ProductToCartPersistence productToCartPersistence = ProductToCartPersistence.getInstance();
        productToCartPersistence.addProductsToCart(cartItem);
    }
}
