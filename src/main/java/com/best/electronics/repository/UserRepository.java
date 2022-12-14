package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Order;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRepository {

    private final IDatabasePersistence databasePersistence;

    public UserRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Order> getUserOrderDetails(Integer userId) {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
            ArrayList<Object> userIdList = new ArrayList<>();
            userIdList.add(userId);
            ArrayList<Map<String, Object>> orders = databasePersistence.loadData("{call get_order_details(?)}", userIdList);
            System.out.println("Order Details: " + orders);
            for (Map<String, Object> order : orders) {
                Order o = new Order();
                o.setOrderId((Integer) order.get("orderId"));
                o.setOrderStatus((String) order.get("orderStatus"));
                o.setOrderDate(String.valueOf(order.get("orderDate")));
                o.setOrderAmount((Double) order.get("orderAmount"));
                o.setPaymentMethod((String) order.get("paymentMethod"));
                o.setAddress((String) order.get("address"));

                ArrayList<Object> orderIdList = new ArrayList<>();
                orderIdList.add(order.get("orderId"));
                ArrayList<Map<String, Object>> orderItems = databasePersistence.loadData("{call get_ordered_product_details(?)}", orderIdList);
                ArrayList<Product> product = new ArrayList<>();
                System.out.println(orderItems);
                for (Map<String, Object> orderItem : orderItems) {
                    Product p = new Product();
                    p.setProductPrice((Double) orderItem.get("productPrice"));
                    p.setProductCode(String.valueOf(orderItem.get("productCode")));
                    p.setProductQuantity((Integer) orderItem.get("quantity"));
                    p.setProductSubtotal((Double) orderItem.get("subTotal"));
                    p.setProductName((String) orderItem.get("productName"));
                    product.add(p);
                }
                o.setProducts(product);
                orderList.add(o);
            }
            return orderList;
        } catch (Exception e) {
            return orderList;
        }
    }

    public String updateUserDetails(User user) {
        try {
            ArrayList<Object> updatedDetails = new ArrayList<>();
            if (isUsernameValid(user.getFirstName()) && isUsernameValid(user.getLastName())) {
                updatedDetails.add(user.getEmailAddress());
                updatedDetails.add(user.getFirstName());
                updatedDetails.add(user.getLastName());
                updatedDetails.add(user.getAddress());
                if (databasePersistence.saveData("{call update_user_details(?, ?, ?, ?)}", updatedDetails)) {
                    return "User Profile Updated Successfully";
                }
            } else {
                return "Either firstName or lastName are not in correct format!";
            }
        } catch (Exception e) {
            return "User Profile Updated Failed! Please try again!";
        }
        return "User Profile Updated Failed! Please try again!";
    }

    private Boolean isUsernameValid(String name) {
        String urlPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public Map<String, Object> getUserDetailsById(Integer userId) {
        try {
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(userId);
            ArrayList<Map<String, Object>> userDetails = databasePersistence.loadData("{call get_user_details_by_user_id(?)}", parameters);
            return userDetails.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Map<String, Object>> getAllUsersDetails() {
        try {
            return databasePersistence.loadData("{call get_all_user_details()}", new ArrayList<>());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Boolean saveUserData(ArrayList<Object> params) throws Exception {
        return databasePersistence.saveData("{call create_user(?, ?, ?, ?, ?, ?, ?)}", params);
    }
}
