package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;

public class Admin extends Account{

    private Integer adminId;

    private String firstName;


    private String lastName;

    private String emailAddress;

    private String password;

    private String confirmPassword;

    private Integer token;


    @Override
    public Integer getAccountId() {
        return adminId;
    }
    @Override
    public void setAccountId(Integer adminId) {
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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public Integer getToken() {
        return token;
    }

    @Override
    public void setToken(Integer token) {
        this.token = token;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getAdminDetails(Integer adminId, IDatabasePersistence databasePersistence){
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(adminId);
            ArrayList<Map<String, Object>> adminDetails = databasePersistence.loadData("{call get_admin_details(?)}", parameters);
            return adminDetails.get(0);
        }catch(Exception e){
            return null;
        }
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
            }

            return orderList;
        } catch (Exception e) {
            return null;
        }
    }
}
