package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.WishListItem;

import java.util.ArrayList;
import java.util.Map;

public class WishListRepository {

    private final IDatabasePersistence databasePersistence;

    public WishListRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Map<String, Object>> getWishListDetails(int id) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        return databasePersistence.loadData("{call getWishlistDetails(?)}", tokenDetails);
    }

    public void removeProductFromWishlist(WishListItem wishListItem) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(wishListItem.getWishListItemIdNumber());
        try {
            databasePersistence.saveData("{call delete_item_from_wishlist(?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductsToWishlist(WishListItem wishListItem) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(wishListItem.getUserId());
        tokenDetails.add(wishListItem.getWishListItemId());
        try {
            databasePersistence.saveData("{call save_product_to_wishlist(?,?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
