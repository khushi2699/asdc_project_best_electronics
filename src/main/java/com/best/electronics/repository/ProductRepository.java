package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.HashMap;
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

    public ArrayList<Map<String, Object>> getProductByCategory(Integer categoryId){
        ArrayList<Map<String, Object>> productDetails = new ArrayList<>();
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(categoryId);
            productDetails =databasePersistence.loadData("{call get_products_by_category(?)}", parameters);
            return productDetails;
        }catch(Exception e){
            return productDetails;
        }
    }

    public ArrayList<Map<String, Object>> getAllProductsAndTheirCategory() throws Exception {
        ArrayList<Map<String, Object>> categories = getProductCategory();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for(Map<String, Object> category: categories){
            ArrayList<Map<String, Object>> allProductsBasedOnId = getProductByCategory((Integer) category.get("categoryId"));
            Map<String, Object> map = new HashMap<>();
            map.put("categoryId", category.get("categoryId"));
            map.put("name", category.get("name"));
            map.put("description", category.get("description"));
            map.put("products", allProductsBasedOnId);
            result.add(map);
        }
        return result;
    }
}
