package com.best.electronics.database;

import java.util.ArrayList;

public class RemoveFullCartPersistence {
    private static RemoveFullCartPersistence removeFullCartPersistence = null;

    public static RemoveFullCartPersistence getInstance(){
        if(removeFullCartPersistence == null){
            removeFullCartPersistence = new RemoveFullCartPersistence();
        }
        return removeFullCartPersistence;
    }

    public void removeFullCart(Integer id){
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
}
