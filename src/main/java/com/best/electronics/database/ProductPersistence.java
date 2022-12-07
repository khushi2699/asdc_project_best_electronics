package com.best.electronics.database;
import java.util.ArrayList;
import java.util.Map;


public class ProductPersistence {

    private static ProductPersistence single_instance = null;

    private ProductPersistence()
    {}

    public static ProductPersistence getInstance()
    {
        if (single_instance == null)
            single_instance = new ProductPersistence();

        return single_instance;
    }
    public ArrayList<Map<String, Object>> getDetails(IDatabasePersistence p) throws Exception {

        ArrayList<Map<String, Object>> result = new ArrayList<>();
       result = p.loadData("Select * from Product");

       return result;
    }
}
