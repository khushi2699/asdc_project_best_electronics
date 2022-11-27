package com.best.electronics.controller;

import com.best.electronics.database.DatabaseConnection;
import com.best.electronics.database.DatabasePersistence;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String products(Model model){
//        Product p = new Product();
//        List<Product> listProducts = p.findProductByID(IDatabasePersistence db);
//
//        model.addAttribute("listProducts", listProducts);
        return "productList";
    }


}
