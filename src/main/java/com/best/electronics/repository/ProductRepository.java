package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class ProductRepository {
    private final IDatabasePersistence databasePersistence;

    public ProductRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Map<String, Object>> getProductDetails() throws Exception {
        return databasePersistence.loadData("{call get_product_list()}", new ArrayList<>());
    }


    public ArrayList<Map<String, Object>> getProductCategory() throws Exception {
        return databasePersistence.loadData("{call get_product_category()}", new ArrayList<>());
    }
}
