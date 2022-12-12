package com.best.electronics.cart_and_wishlist;

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
        if(this.cartItem != null && this.cartItem.getIdentifier().equalsIgnoreCase("Cart")){
            //here adding product to cart logic
            CartAddCommand cartAddCommand = new CartAddCommand(this.cartItem);
            cartAddCommand.execute();
        }
        else if (this.wishListItem.getIdentifier().equalsIgnoreCase("Wishlist")){
            //here adding product to wishlist logic
            WishlistAddCommand wishlistAddCommand = new WishlistAddCommand(this.wishListItem);
            wishlistAddCommand.execute();
        } else {

        }
    }

    public void Remove(){
        if(this.cartItem != null && this.cartItem.getIdentifier().equalsIgnoreCase("Cart")){
            //here adding product to cart logic
            CartRemoveCommand cartRemoveCommand = new CartRemoveCommand(this.cartItem);
            cartRemoveCommand.execute();
        }
        else if (this.wishListItem.getIdentifier().equalsIgnoreCase("Wishlist")){
            //here adding product to wishlist logic
            WishlistRemoveCommand wishlistRemoveCommand = new WishlistRemoveCommand(this.wishListItem);
            wishlistRemoveCommand.execute();
        } else {

        }
    }

}
