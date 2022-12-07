package com.best.electronics.controller;
import com.best.electronics.database.DatabasePersistence;
import com.best.electronics.database.ProductPersistence;
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
    public String products(Model model) throws Exception {

        ProductPersistence productPersistence = ProductPersistence.getInstance();
        DatabasePersistence db = new DatabasePersistence();

        ArrayList<Map<String, Object>> productList = productPersistence.getDetails(db);
        Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);
        logger.info("Outside product persistence");
        logger.info(String.valueOf(productList));
        //logger.info(String.valueOf(listProducts));

        model.addAttribute("listProducts", productList);
        return "productList";
    }


}
