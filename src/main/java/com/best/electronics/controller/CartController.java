package com.best.electronics.controller;

import com.best.electronics.cart_and_wishlist.Invoker;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.database.ProductToCartPersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class CartController {
    @PostMapping("/CartController/{product_id}")
    public String index(Product product, HttpServletRequest request, @PathVariable Integer product_id, Model model) throws Exception {

        System.out.println("Quantity" +request.getParameter("userQuantity"));
        Integer quantity = Integer.valueOf(request.getParameter("userQuantity"));

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("userId");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                CartItem cartItem = new CartItem(product_id, "Cart", quantity, id);
                Invoker invoker = new Invoker();
                invoker.setCommand(cartItem, null);
                invoker.Add();
            }
        }

        ProductPersistence productPersistence = ProductPersistence.getInstance();
        IDatabasePersistence db = new MySQLDatabasePersistence();
        ArrayList<Map<String, Object>> productList = productPersistence.getDetails(db);
        model.addAttribute("listProducts", productList);
        return "productList";
    }
}

