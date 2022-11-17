package com.best.electronics.controller;

import com.best.electronics.entity.Product;
import com.best.electronics.service.implementation.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String products(Model model){
        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);

        return "productList";
    }
}
