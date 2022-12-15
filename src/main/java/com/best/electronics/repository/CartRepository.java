package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.CardDetails;
import com.best.electronics.model.CartItem;

import java.util.ArrayList;
import java.util.Map;

public class CartRepository {
    private final IDatabasePersistence databasePersistence;

    public CartRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Map<String, Object>> getCartListDetails(int id) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        return databasePersistence.loadData("{call getCartDetails(?)}", tokenDetails);
    }

    public Integer getOrderId(int id) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        ArrayList<Map<String, Object>> result = databasePersistence.loadData("{call getOrderId(?)}", tokenDetails);
        return (Integer) result.get(0).get("orderId");
    }

    public ArrayList<Map<String, Object>> getCardDetails(int userId) throws Exception {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(userId);
        return databasePersistence.loadData("{call getCardDetails(?)}", tokenDetails);
    }

    public void removeProductFromCart(CartItem cartItem) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cartItem.getCartItemId());
        tokenDetails.add(cartItem.getUserId());
        try {
            databasePersistence.saveData("{call delete_item_from_cart(?,?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductsToCart(CartItem cartItem) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cartItem.getUserId());
        tokenDetails.add(cartItem.getCartItemId());
        tokenDetails.add(cartItem.getQuantity());

        try {
            databasePersistence.saveData("{call save_product_to_cart(?,?,?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFullCart(Integer id) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        try {
            databasePersistence.saveData("{call removeCart(?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCard(CardDetails cardDetails) {
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cardDetails.getCardName());
        tokenDetails.add(cardDetails.getSecurityCode());
        tokenDetails.add(cardDetails.getExpiryDate());
        tokenDetails.add(cardDetails.getUserId());
        tokenDetails.add(cardDetails.getCardType());
        tokenDetails.add(cardDetails.getCardNumber());
        try {
            databasePersistence.saveData("{call savecardDetails(?,?,?,?,?,?)}", tokenDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
