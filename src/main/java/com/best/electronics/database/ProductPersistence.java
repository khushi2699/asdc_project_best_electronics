package com.best.electronics.database;
import java.util.ArrayList;
import java.util.Map;

import exceptions.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProductPersistence {

    private static ProductPersistence single_instance = null;

    private ProductPersistence()
    {}

    Logger logger = (Logger) LoggerFactory.getLogger(ProductPersistence.class);
    public static ProductPersistence getInstance()
    {
        if (single_instance == null)
            single_instance = new ProductPersistence();

        return single_instance;
    }
    public ArrayList<Map<String, Object>> getDetails(IDatabasePersistence p) throws Exception {

        ArrayList<Map<String, Object>> result = new ArrayList<>();
       result = p.loadData("{call get_product_list()}");
       if(result.isEmpty()){
           throw new DataNotFoundException("Unable to load product list");
       }
       else {
           return result;
       }
    }
}
