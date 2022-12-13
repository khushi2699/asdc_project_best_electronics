package com.best.electronics.controller;

import com.best.electronics.cartandwishlist.Invoker;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.repository.ProductRepository;
import com.best.electronics.repository.UserRepository;
import com.best.electronics.model.CartItem;
import com.best.electronics.model.WishList;
import com.best.electronics.model.WishListItem;
import com.best.electronics.repository.WishListRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class WishListController {
    @PostMapping("/WishlistController/{product_id}")
    public String index(HttpServletRequest request, @PathVariable Integer product_id, Model model) throws Exception {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
            if(userDetail != null){
                WishListItem wishListItem = new WishListItem(product_id, "Wishlist", id);
                Invoker invoker = new Invoker();
                invoker.setCommand(null, wishListItem);
                invoker.Add();
            }
        }

        IDatabasePersistence db = new MySQLDatabasePersistence();
        ProductRepository productRepository = new ProductRepository(db);
        ArrayList<Map<String, Object>> productList = productRepository.getProductDetails();
        model.addAttribute("listProducts", productList);
        return "productList";
    }

    @GetMapping("/wishList")
    public String displayWishlist(Model model, HttpServletRequest request) throws Exception {

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
            if(userDetail != null){
                WishListRepository wishListRepository = new WishListRepository(databasePersistence);
                ArrayList<Map<String, Object>> wishListResult = wishListRepository.getWishListDetails(id);
                if(wishListResult == null){}
                else {
                model.addAttribute("wishlist",new WishList());
                model.addAttribute("listWishlist", wishListResult);}
            }

        }
        return "wishList";
    }

    @PostMapping("/WishlistControllerToCart/{product_id}")
    public String moveItemToCart(HttpServletRequest request, @PathVariable Integer product_id, Model model){
        model.addAttribute("wishlist",new WishList());
        Integer quantity = Integer.valueOf(request.getParameter("userQuantity"));
        Integer wishListItemId = Integer.valueOf(request.getParameter("wishListItemId"));

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
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
    public String removeItemFromWishlist(HttpServletRequest request, @PathVariable Integer product_id, Model model){
        model.addAttribute("wishlist", new WishList());
        Integer wishListItemId = Integer.valueOf(request.getParameter("wishListItemId"));

        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            Integer id = (Integer) oldSession.getAttribute("id");
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            UserRepository userRepository = new UserRepository(databasePersistence);
            Map<String, Object> userDetail = userRepository.getUserDetailsById(id);
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
