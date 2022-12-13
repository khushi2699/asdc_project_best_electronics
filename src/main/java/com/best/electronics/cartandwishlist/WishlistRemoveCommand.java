package com.best.electronics.cartandwishlist;

import com.best.electronics.database.ProductRemovePersistence;
import com.best.electronics.model.WishListItem;

public class WishlistRemoveCommand implements CommandForProduct {
    WishListItem wishListItem;

    public WishlistRemoveCommand(WishListItem wishListItem){
        this.wishListItem = wishListItem;
    }
    @Override
    public void execute() {
        ProductRemovePersistence productRemovePersistence = ProductRemovePersistence.getInstance();
        productRemovePersistence.removeProductFromWishlist(wishListItem);
    }
}
