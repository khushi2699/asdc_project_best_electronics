package com.best.electronics.database;

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
        ArrayList<Object> tokenDetails = new ArrayList<>();
        tokenDetails.add(id);
        return databasePersistence.loadData("{call getWishlistDetails(?)}", tokenDetails);
    }
}
