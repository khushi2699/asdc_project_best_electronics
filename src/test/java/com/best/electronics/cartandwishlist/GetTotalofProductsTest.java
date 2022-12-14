package com.best.electronics.cartandwishlist;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class GetTotalofProductsTest {

    GetTotalOfProduct getTotalOfProduct;

    @Before
    public void init(){
        getTotalOfProduct = new GetTotalOfProduct();
    }

    @Test
    public void calculateTotalOfProductsSuccessTest(){
        ArrayList<Map<String,Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("quantity", 1);
        map.put("productPrice", 100.0);
        result.add(map);

        double sum = getTotalOfProduct.calculateTotalOfProducts(result);
        Assertions.assertEquals(sum , 100.0);
    }

    @Test
    public void calculateTotalOfProductsFailureTest(){
        ArrayList<Map<String,Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("quantity", 1);
        map.put("productPrice", 100.0);
        result.add(map);

        double sum = getTotalOfProduct.calculateTotalOfProducts(result);
        Assertions.assertNotEquals(sum , 120.0);
    }
}
