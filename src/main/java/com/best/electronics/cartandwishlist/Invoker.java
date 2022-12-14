package com.best.electronics.cartandwishlist;

import com.best.electronics.model.CartItem;
import com.best.electronics.model.WishListItem;

public class Invoker {
    CartItem cartItem = new CartItem();

    WishListItem wishListItem = new WishListItem();

    public void setCommand(CartItem cartItem , WishListItem wishListItem){
        this.cartItem = cartItem;
        this.wishListItem = wishListItem;
    }

    public void Add(){
        if(this.cartItem == null && this.wishListItem.getIdentifier().equalsIgnoreCase("Wishlist")){
            //here adding product to wishlist logic
            WishlistAdd wishlistAdd = new WishlistAdd(this.wishListItem);
            wishlistAdd.execute();

        }
        else if (this.cartItem.getIdentifier().equalsIgnoreCase("Cart") && this.wishListItem == null){
            //here adding product to cart logic
            CartAdd cartAdd = new CartAdd(this.cartItem);
            cartAdd.execute();
        }
    }

    public void Remove(){
        if(this.cartItem == null && this.wishListItem.getIdentifier().equalsIgnoreCase("Wishlist")){
            //here removing product from wishlist logic
            WishlistRemove wishlistRemove = new WishlistRemove(this.wishListItem);
            wishlistRemove.execute();
        }
        else if (this.cartItem.getIdentifier().equalsIgnoreCase("Cart") && this.wishListItem == null){
            //here removing product from cart logic
            CartRemove cartRemove = new CartRemove(this.cartItem);
            cartRemove.execute();
        }
    }
}
