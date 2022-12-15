package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
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
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        ArrayList<Map<String, Object>> result = databasePersistence.loadData("{call getOrderId(?)}", tokenDetails);
        return (Integer) result.get(0).get("orderId");
    }

    public ArrayList<Map<String, Object>> getCardDetails(int userId) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(userId);
        return databasePersistence.loadData("{call getCardDetails(?)}", tokenDetails);
    }

    public void removeProductFromCart(CartItem cartItem) {
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

    public void addProductsToCart(CartItem cartItem) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cartItem.getUserId());
        tokenDetails.add(cartItem.getCartItemId());
        tokenDetails.add(cartItem.getQuantity());

        try {
            if (databasePersistence.saveData("{call save_product_to_cart(?,?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFullCart(Integer id) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        try {
            if (databasePersistence.saveData("{call removeCart(?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCard(CardDetails cardDetails) {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(cardDetails.getCardName());
        tokenDetails.add(cardDetails.getSecurityCode());
        tokenDetails.add(cardDetails.getExpiryDate());
        tokenDetails.add(cardDetails.getUserId());
        tokenDetails.add(cardDetails.getCardType());
        tokenDetails.add(cardDetails.getCardNumber());
        try {
            if (databasePersistence.saveData("{call savecardDetails(?,?,?,?,?,?)}", tokenDetails)) {
                System.out.println("Done");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
