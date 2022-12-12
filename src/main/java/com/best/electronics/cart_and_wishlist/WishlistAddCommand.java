package com.best.electronics.cart_and_wishlist;

import com.best.electronics.database.ProductToCartPersistence;
import com.best.electronics.database.ProductToWishlistPersistence;
import com.best.electronics.model.WishListItem;

public class WishlistAddCommand implements CommandForProduct{

    WishListItem wishListItem;

    public WishlistAddCommand(WishListItem wishListItem){
        this.wishListItem = wishListItem;
    }
    @Override
    public void execute() {
        ProductToWishlistPersistence productToWishlistPersistence = ProductToWishlistPersistence.getInstance();
        productToWishlistPersistence.addProductsToWishlist(wishListItem);
    }
}
