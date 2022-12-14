package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Admin;
import com.best.electronics.model.Product;
import com.best.electronics.model.ProductCategory;

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

    public Map<String, Object> getProductDetails(Integer productId){
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(productId);
            ArrayList<Map<String, Object>> productDetails = databasePersistence.loadData("{call get_products_by_id(?)}", parameters);
            return productDetails.get(0);
        }catch(Exception e){
            return null;
        }
    }

    public String updateProductDetails(Product product){
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();
                updatedDetails.add(product.getProductId());
                updatedDetails.add(product.getProductQuantity());
                updatedDetails.add(product.getProductPrice());
                if(databasePersistence.saveData("{call update_product_details(?, ?, ?)}", updatedDetails)){
                    return "Product Details Updated Successfully";
                }
        }catch(Exception e){
            return "Product details Updated Failed! Please try again!";
        }
        return "Product details  Updated Failed! Please try again!";
    }

    public ArrayList<Map<String, Object>> getProductCategory() throws Exception {
        return databasePersistence.loadData("{call get_product_category()}", new ArrayList<>());
    }
    public ArrayList<Map<String, Object>> getProductByCategory(Integer categoryId){
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(categoryId);
            return databasePersistence.loadData("{call get_products_by_category(?)}", parameters);
        }catch(Exception e){
            return null;
        }
    }
    public ArrayList<Map<String, Object>> getAllProductsAndTheirCategory() throws Exception {
        ArrayList<Map<String, Object>> categories = getProductCategory();
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for(Map<String, Object> category: categories){
            ArrayList<Map<String, Object>> allProductsbasedOnId = getProductByCategory((Integer) category.get("categoryId"));
            Map<String, Object> map = new HashMap<>();
            map.put("categoryId", category.get("categoryId"));
            map.put("name", category.get("name"));
            map.put("description", category.get("description"));
            map.put("products", allProductsbasedOnId);
            result.add(map);
        }
        return result;
    }

    public String createProduct(Product product, Integer id) {
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();
            updatedDetails.add(id);
            updatedDetails.add(product.getProductCode());
            updatedDetails.add(product.getProductName());
            updatedDetails.add(product.getProductBrand());
            updatedDetails.add(product.getProductDescription());
            updatedDetails.add(product.getProductPrice());
            updatedDetails.add(product.getProductQuantity());
            System.out.println("updatedDetails"+updatedDetails);
            if(databasePersistence.saveData("{call create_product(?, ?, ?, ?, ?, ?, ?)}", updatedDetails)){
                return "Product Created Successfully";
            }
        }catch(Exception e){
            return "Product Creation Failed! Please try again!";
        }
        return "Product Creation Failed! Please try again!";
    }

    public String createCategory(ProductCategory productCategory) {
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();
            updatedDetails.add(productCategory.getProductCategoryName());
            updatedDetails.add(productCategory.getProductCategoryDescription());
            System.out.println("updatedDetails"+updatedDetails);
            if(databasePersistence.saveData("{call create_category( ?, ?)}", updatedDetails)){
                return "Product category Created Successfully";
            }
        }catch(Exception e){
            return "Product category Creation Failed! Please try again!";
        }
        return "Product category Creation Failed! Please try again!";
    }
    
}
