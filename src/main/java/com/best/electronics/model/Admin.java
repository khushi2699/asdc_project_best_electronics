package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class Admin {

    private String adminId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private int userId;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public ArrayList<Order> getOrderDetails(IDatabasePersistence databasePersistence) {
        try {
            ArrayList<Order> orderList = new ArrayList<>();

            ArrayList<Map<String, Object>> orders = databasePersistence.loadData("Select * from OrderDetails", new ArrayList<>());
            System.out.println("Order Details: " + orders);

            for (Map<String, Object> order : orders) {
                Order o = new Order();
                o.setOrderId((Integer) order.get("orderId"));
                o.setOrderStatus((String) order.get("orderStatus"));
                o.setOrderDate(String.valueOf(order.get("orderDate")));
                o.setOrderAmount((Double) order.get("orderAmount"));
                o.setPaymentMethod((String) order.get("paymentMethod"));
                o.setAddress((String) order.get("address"));
                o.setUserId((Integer) order.get("userId"));


                Integer userId = (Integer) order.get("userId");
                ArrayList<Map<String, Object>> userInfo = databasePersistence.loadData("Select * from User where userId=" + userId, new ArrayList<>());
                System.out.println("User Details: " + userInfo);
                Map<String, Object> user = userInfo.get(0);

                    User u = new User();
                    u.setFirstName((String) user.get("firstName"));
                    u.setLastName((String) user.get("lastName"));
                    u.setEmailAddress((String) user.get("emailAddress"));
                    u.setAddress((String) user.get("address"));

                o.setUser(u);
                orderList.add(o);
//
            }

            return orderList;
        } catch (Exception e) {
            return null;
        }
    }
}
