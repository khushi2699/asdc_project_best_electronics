package com.best.electronics.model;

public class WishListItem {

    private Integer wishListItemId;

    private String identifier;

    private Integer userId;

    public WishListItem()
    {

    }
    public WishListItem(Integer wishListItemId, String identifier, Integer userId){
        this.wishListItemId = wishListItemId;
        this.userId = userId;
        this.identifier = identifier;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getWishListItemId() {
        return wishListItemId;
    }

    public void setWishListItemId(Integer wishListItemId) {
        this.wishListItemId = wishListItemId;
    }
}
