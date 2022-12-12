package com.best.electronics.database;

import com.best.electronics.model.CartItem;
import com.best.electronics.model.WishListItem;

import java.util.ArrayList;

public class ProductToWishlistPersistence {

    private static ProductToWishlistPersistence productToWishlistPersistence = null;

    public static ProductToWishlistPersistence getInstance(){
        if(productToWishlistPersistence == null){
            productToWishlistPersistence = new ProductToWishlistPersistence();
        }
        return productToWishlistPersistence;
    }

    public void addProductsToWishlist(WishListItem wishListItem){
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(wishListItem.getUserId());
        tokenDetails.add(wishListItem.getWishListItemId());

        try {
            if (databasePersistence.saveData("{call save_product_to_wishlist(?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
