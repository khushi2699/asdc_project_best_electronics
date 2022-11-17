package com.best.electronics.repository;

import com.best.electronics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductById(Long productId);
}