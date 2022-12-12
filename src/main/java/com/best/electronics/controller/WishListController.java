package com.best.electronics.controller;

import com.best.electronics.cart_and_wishlist.Invoker;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;
import com.best.electronics.model.WishListItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class WishListController {
    @PostMapping("/WishlistController/{product_id}")
    public String index(Product product, HttpServletRequest request, @PathVariable Integer product_id, Model model) throws Exception {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("userId");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                WishListItem wishListItem = new WishListItem(product_id, "Wishlist", id);
                Invoker invoker = new Invoker();
                invoker.setCommand(null, wishListItem);
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
