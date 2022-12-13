package com.best.electronics.database;

import com.best.electronics.model.CartItem;
import com.best.electronics.model.WishListItem;

import java.util.ArrayList;

public class ProductRemovePersistence {

    private static ProductRemovePersistence productRemovePersistence = null;

    public static ProductRemovePersistence getInstance(){
        if(productRemovePersistence == null){
            productRemovePersistence = new ProductRemovePersistence();
        }
        return productRemovePersistence;
    }

    public void removeProductFromWishlist(WishListItem wishListItem){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(wishListItem.getWishListItemIdNumber());
        try {
            if (databasePersistence.saveData("{call delete_item_from_wishlist(?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProductFromCart(CartItem cartItem){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cartItem.getCartItemId());
        tokenDetails.add(cartItem.getUserId());
        try {
            if (databasePersistence.saveData("{call delete_item_from_cart(?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
