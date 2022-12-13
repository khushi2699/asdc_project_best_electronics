package com.best.electronics.cartandwishlist;

import com.best.electronics.database.ProductRemovePersistence;
import com.best.electronics.database.ProductToCartPersistence;
import com.best.electronics.model.CartItem;

public class CartRemoveCommand implements CommandForProduct{
    CartItem cartItem;
    public CartRemoveCommand(CartItem cartItem){
        this.cartItem = cartItem;
    }

    @Override
    public void execute() {
        ProductRemovePersistence productRemovePersistence = ProductRemovePersistence.getInstance();
        productRemovePersistence.removeProductFromCart(cartItem);
    }

}
