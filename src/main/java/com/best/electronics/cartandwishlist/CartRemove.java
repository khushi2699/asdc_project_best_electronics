package com.best.electronics.cartandwishlist;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.repository.CartRepository;

public class CartRemove implements CommandForProduct{
    CartItem cartItem;
    public CartRemove(CartItem cartItem){
        this.cartItem = cartItem;
    }

    @Override
    public void execute() {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        CartRepository cartRepository = new CartRepository(databasePersistence);
        cartRepository.removeProductFromCart(cartItem);
    }

}
