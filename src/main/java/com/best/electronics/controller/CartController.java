package com.best.electronics.controller;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class CartController {
    @PostMapping("/CartController/{product_id}")
    public String index(Product product, HttpServletRequest request, @PathVariable Integer product_id, Model model) throws Exception {

        System.out.println(request.getParameter("userQuantity"));
        Integer quantity = Integer.valueOf(request.getParameter("userQuantity"));
        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> productList = productPersistence.getDetails(db);
        model.addAttribute("listProducts", productList);
        return "productList";
    }
}

