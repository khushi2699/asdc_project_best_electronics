package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void setPassword(String password) {this.password = password;}

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

                Integer orderId = (Integer) order.get("orderId");
                ArrayList<Map<String, Object>> orderItems = databasePersistence.loadData("Select * from OrderItem where orderId=" + orderId, new ArrayList<>());
                ArrayList<Product> product = new ArrayList<>();
                for(Map<String, Object> orderItem: orderItems) {
                    Integer productId = (Integer) orderItem.get("productId");
                    ArrayList<Map<String, Object>> productDetails = databasePersistence.loadData("Select * from Product where productId=" + productId, new ArrayList<>());
                    Map<String, Object> productDetail = productDetails.get(0);

                    Product p = new Product();
                    p.setProductId((Integer) orderItem.get("productId"));
                    p.setProductQuantity((Integer) orderItem.get("quantity"));
                    p.setProductPrice((Double) orderItem.get("subTotal"));
                    p.setProductName((String) productDetail.get("productName"));
                    product.add(p);
                }
                o.setProducts(product);
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

    private Boolean isUsernameValid(String name) {
        String urlPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
    public String updateAdminDetails(IDatabasePersistence databasePersistence){
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();

            if(isUsernameValid(this.getFirstName()) && isUsernameValid(this.getLastName())){
                updatedDetails.add(this.getEmailAddress());
                updatedDetails.add(this.getFirstName());
                updatedDetails.add(this.getLastName());
                if(databasePersistence.saveData("{call update_admin_details(?, ?, ?)}", updatedDetails)){
                    return "Admin Profile Updated Successfully";
                }
            }else{
                return "Either firstName or lastName are not in correct format!";
            }
        }catch(Exception e){
            return "Admin Profile Updated Failed! Please try again!";
        }
        return "Admin Profile Updated Failed! Please try again!";
    }
}
