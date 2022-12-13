package com.best.electronics.controller;

import com.best.electronics.cartandwishlist.CartRemoveCommand;
import com.best.electronics.cartandwishlist.GetTotalOfProduct;
import com.best.electronics.cartandwishlist.Invoker;
import com.best.electronics.database.*;
import com.best.electronics.model.*;
import com.best.electronics.database.GetCartListPersistence;
import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.database.MySQLDatabasePersistence;
import com.best.electronics.database.ProductPersistence;
import com.best.electronics.model.CartItem;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
public class CartController {
    @PostMapping("/CartController/{product_id}")
    public String index(Product product, HttpServletRequest request, @PathVariable Integer product_id, Model model) throws Exception {

        System.out.println("Quantity" + request.getParameter("userQuantity"));
        Integer quantity = Integer.valueOf(request.getParameter("userQuantity"));

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if (userDetail != null) {
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

    @GetMapping("/cart")
    public String displayWishlist(Model model, HttpServletRequest request) throws Exception {

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if (userDetail != null) {
                GetCartListPersistence getCartListPersistence = GetCartListPersistence.getInstance();
                ArrayList<Map<String, Object>> cartListResult = getCartListPersistence.getCartListDetails(id);
                if (cartListResult == null) {
                } else {
                    GetTotalOfProduct getTotalOfProduct = GetTotalOfProduct.getInstance();
                    double totalsum = getTotalOfProduct.calculateTotalOfProducts(cartListResult);
                    System.out.println("Total sum "+ totalsum);
                    model.addAttribute("sumofcart",totalsum);
                    model.addAttribute("cart", new Product());
                    model.addAttribute("listCart", cartListResult);
                }
            }

        }
        return "cart";
    }

    @PostMapping("addCardDetails")
    public String addCardDetails(HttpServletRequest request, Model model) throws Exception {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if (userDetail != null) {
                CardDetails cardDetails = new CardDetails();
                cardDetails.setCardName(request.getParameter("cardName"));
                cardDetails.setCardNumber(request.getParameter("cardNumber"));
                cardDetails.setCardType(request.getParameter("cardType"));
                cardDetails.setExpiryDate(request.getParameter("cardExpiry"));
                cardDetails.setSecurityCode(request.getParameter("securityCode"));
                cardDetails.setUserId(id);
                SaveCardDetailsPersistence saveCardDetailsPersistence = SaveCardDetailsPersistence.getInstance();
                saveCardDetailsPersistence.saveCard(cardDetails);
            }
        }
        return "redirect:/proceedToOrder";
    }

    @GetMapping("proceedToOrder")
    public String proceedToOrder(Model model, HttpServletRequest request) throws Exception {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if (userDetail != null) {
                GetCartListPersistence getCartListPersistence = GetCartListPersistence.getInstance();
                ArrayList<Map<String, Object>> cartListResult = getCartListPersistence.getCartListDetails(id);
                if (cartListResult == null) {
                } else {
                    //getting total sum of the cart
                    GetTotalOfProduct getTotalOfProduct = GetTotalOfProduct.getInstance();
                    double totalsum = getTotalOfProduct.calculateTotalOfProducts(cartListResult);

                    //getting address details of user
                    model.addAttribute("address", userDetail.get("address"));

                    //getting payment method
                    GetUserPaymentDetailPersistence getUserPaymentDetail = GetUserPaymentDetailPersistence.getInstance();
                    ArrayList<Map<String, Object>> paymentListDetails = getUserPaymentDetail.getCardDetails(id);

                    model.addAttribute("cardDetails",paymentListDetails);
                    model.addAttribute("sumofcart",totalsum);
                }
            }
        }
        return "proceedToCheckout";
    }
    @PostMapping("/placeOrder")
    public String placeOrder(HttpServletRequest request, Model model) throws Exception {
        Order order = new Order();
        order.setOrderAmount(0.0);
        order.setOrderStatus("Order placed");
        order.setPaymentMethod(request.getParameter("payment"));

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            order.setUserId(id);
            order.setAddress((String) userDetail.get("address"));
            order.setOrderDate(String.valueOf(new Date()));

            if (userDetail != null) {
                GetCartListPersistence getCartListPersistence = GetCartListPersistence.getInstance();
                ArrayList<Map<String, Object>> cartListResult = getCartListPersistence.getCartListDetails(id);
                if (cartListResult == null) {
                } else {
                    //placing order here

                    //adding data to order Item generating the code and then adding it into the orderDetails table
                    PlaceOrderPersistence placeOrderPersistence = PlaceOrderPersistence.getInstance();
                    placeOrderPersistence.placeorder(order);
                    //order added to OrderDetails

                    //fetching latest order for user to add data in orderItem
                    GetOrderPersistence getOrderPersistence = GetOrderPersistence.getInstance();
                    int latestOrderDetailsId = getOrderPersistence.getOrderId(id);
                    //using the extracted orderDetailsID to add orderItem

                    PlaceOrderItemPersistence placeOrderItemPeristence = PlaceOrderItemPersistence.getInstance();
                    placeOrderItemPeristence.saveOrderItems(cartListResult, latestOrderDetailsId);
                    //Order Items added to the db

                    //Removing cart Items
                    RemoveFullCartPersistence removeFullCartPersistence = RemoveFullCartPersistence.getInstance();
                    removeFullCartPersistence.removeFullCart(id);

                }
            }
        }
        return "cart";
    }

    @PostMapping("/RemoveFromCartlistController/{product_id}")
    public String removeFromCart(Product product, HttpServletRequest request, Model model) throws Exception {

        int cardItemId = Integer.parseInt(request.getParameter("cartItemId"));

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            Integer id = (Integer) oldSession.getAttribute("id");
            User user = new User();
            IDatabasePersistence databasePersistence = new MySQLDatabasePersistence();
            Map<String, Object> userDetail = user.getUserDetails(id, databasePersistence);
            if (userDetail != null) {
                CartItem cartItem = new CartItem(cardItemId,"Cart",id);
                Invoker invoker1 = new Invoker();
                invoker1.setCommand(cartItem, null);
                invoker1.Remove();
            }
        }
        return "redirect:/cart";
    }


}