package com.best.electronics.service.implementation;

import com.best.electronics.entity.Product;
import com.best.electronics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


public class ProductService {
    @Autowired private ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }
}
