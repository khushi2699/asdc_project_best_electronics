package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.exceptions.DataNotFoundException;

import java.util.ArrayList;
import java.util.Map;

public class ProductRepository {
    private final IDatabasePersistence databasePersistence;

    public ProductRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Map<String, Object>> getProductDetails() throws Exception {
        ArrayList<Map<String, Object>> result = databasePersistence.loadData("{call get_product_list()}", new ArrayList<>());
        if(result.isEmpty()){
            throw new DataNotFoundException("Unable to load product list");
        } else {
            return result;
        }
    }
}
