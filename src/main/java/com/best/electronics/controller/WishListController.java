package com.best.electronics.controller;

import com.best.electronics.cart_and_wishlist.Invoker;
import com.best.electronics.database.GetWishlistPersistence;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            Integer id = (Integer) oldSession.getAttribute("id");
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

    @GetMapping("/wishList")
    public String displayWishlist(Model model, HttpServletRequest request) throws Exception {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                GetWishlistPersistence getWishlistPersistence = GetWishlistPersistence.getInstance();
                ArrayList<Map<String, Object>> wishListResult = getWishlistPersistence.getWishListDetails(id);
                if(wishListResult == null){}
                else {
                model.addAttribute("wishlist",new WishList());
                model.addAttribute("listWishlist", wishListResult);}
            }

        }
        return "wishList";
    }

    @PostMapping("/WishlistControllerToCart/{product_id}")
    public String moveItemToCart(@ModelAttribute WishList wishlist, HttpServletRequest request, @PathVariable Integer product_id, Model model){
        model.addAttribute("wishlist",new WishList());
        Integer quantity = Integer.valueOf(request.getParameter("userQuantity"));
        Integer wishListItemId = Integer.valueOf(request.getParameter("wishListItemId"));

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                //Adding the item to cart
                CartItem cartItem = new CartItem(product_id, "Cart", quantity, id);
                Invoker invoker = new Invoker();
                invoker.setCommand(cartItem, null);
                invoker.Add();
                //Removing the item from wishlist
                WishListItem wishListItem = new WishListItem(product_id, "Wishlist", id , wishListItemId);
                Invoker invoker1 = new Invoker();
                invoker1.setCommand(null, wishListItem);
                invoker1.Remove();
            }
        }
        return "redirect:/wishList";
    }

    @PostMapping("/RemoveFromWishList/{product_id}")
    public String removeItemFromWishlist(@ModelAttribute WishList wishlist, HttpServletRequest request, @PathVariable Integer product_id, Model model){
        model.addAttribute("wishlist",new WishList());
        Integer wishListItemId = Integer.valueOf(request.getParameter("wishListItemId"));

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if(userDetail != null){
                //Removing the item from wishlist
                WishListItem wishListItem = new WishListItem(product_id, "Wishlist", id , wishListItemId);
                Invoker invoker1 = new Invoker();
                invoker1.setCommand(null, wishListItem);
                invoker1.Remove();
            }
        }
        return "redirect:/wishList";
    }
}
