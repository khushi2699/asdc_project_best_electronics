package com.best.electronics.cartandwishlist;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.repository.CartRepository;

public class CartAddCommand implements CommandForProduct{

    CartItem cartItem;

    public CartAddCommand(CartItem cartItem){
        this.cartItem = cartItem;
    }

    @Override
    public void execute() {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        CartRepository cartRepository = new CartRepository(databasePersistence);
        cartRepository.addProductsToCart(cartItem);
    }
}
