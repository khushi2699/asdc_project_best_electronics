package com.best.electronics.database;

import exceptions.DataNotFoundException;

import java.util.ArrayList;
import java.util.Map;

public class GetWishlistPersistence {

    private static GetWishlistPersistence getWishlistPersistence = null;

    public static GetWishlistPersistence getInstance(){
        if(getWishlistPersistence == null){
            getWishlistPersistence = new GetWishlistPersistence();
        }
        return getWishlistPersistence;
    }

    public ArrayList<Map<String, Object>> getWishListDetails(int id) throws Exception {
        IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        result = databasePersistence.loadData("{call getWishlistDetails(?)}", tokenDetails);
        return result;
    }
}
