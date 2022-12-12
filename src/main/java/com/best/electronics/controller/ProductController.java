package com.best.electronics.controller;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.model.Product;
import exceptions.NullPointerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class ProductController {
    @GetMapping("/products")
    public String products(Model model) throws Exception{

        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();

        ArrayList<Map<String, Object>> productList = null;
        productList = productPersistence.getDetails(db);
        Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

        if(productList == null){
            throw new NullPointerException("Product List could not be fetched from the database");
        }
        else {
            model.addAttribute("product", new Product());
            model.addAttribute("listProducts", productList);
            return "productList";
        }
    }



}
