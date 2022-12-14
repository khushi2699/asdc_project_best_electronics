package com.best.electronics.controller;
import com.best.electronics.cartandwishlist.Invoker;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.model.Product;
import com.best.electronics.exceptions.NullPointerException;
import com.best.electronics.repository.ProductRepository;
import com.best.electronics.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/products")
    public String products(Model model) throws Exception{
        IDatabasePersistence db = new MySQLDatabasePersistence();
        ProductRepository productRepository = new ProductRepository(db);
        ArrayList<Map<String, Object>> productList = productRepository.getProductDetails();
        if(productList.isEmpty()){
            throw new NullPointerException("Product List could not be fetched from the database");
        } else {
            model.addAttribute("product", new Product());
            model.addAttribute("listProducts", productList);
            return "productList";
        }
    }


}
