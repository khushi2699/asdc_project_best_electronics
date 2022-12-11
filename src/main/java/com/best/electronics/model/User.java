package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends Account{

    private Integer userId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private String confirmPassword;

    private String dateOfBirth;

    private String gender;

    private String address;

    private Integer token;

    @Override
    public Integer getAccountId() {
        return userId;
    }

    @Override
    public void setAccountId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
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
    public String getPassword() {return password;}

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Integer getToken() {
        return token;
    }

    @Override
    public void setToken(Integer token) {
        this.token = token;
    }

    public String updateUserDetails(IDatabasePersistence databasePersistence){
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();

            if(isUsernameValid(this.getFirstName()) && isUsernameValid(this.getLastName())){
                updatedDetails.add(this.getEmailAddress());
                updatedDetails.add(this.getFirstName());
                updatedDetails.add(this.getLastName());
                updatedDetails.add(this.getAddress());
                if(databasePersistence.saveData("{call update_user_details(?, ?, ?, ?)}", updatedDetails)){
                    return "User Profile Updated Successfully";
                }
            }else{
                return "Either firstName or lastName are not in correct format!";
            }
        }catch(Exception e){
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

    public Map<String, Object> getUserDetails(Integer userId, IDatabasePersistence databasePersistence){
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(userId);
            ArrayList<Map<String, Object>> userDetails = databasePersistence.loadData("{call get_user_details_for_update(?)}", parameters);
            return userDetails.get(0);
        }catch(Exception e){
            return null;
        }
    }

    public ArrayList<Order> getOrderDetails(Integer userId, IDatabasePersistence databasePersistence){
        try{
            ArrayList<Order> orderList = new ArrayList<>();
            ArrayList<Map<String, Object>> orders = databasePersistence.loadData("Select * from OrderDetails where userId=" + userId, new ArrayList<>());
            System.out.println("Order Details: " + orders);

            for(Map<String, Object> order: orders){
                Order o = new Order();
                o.setOrderId((Integer) order.get("orderId"));
                o.setOrderStatus((String) order.get("orderStatus"));
                o.setOrderDate(String.valueOf(order.get("orderDate")));
                o.setOrderAmount((Double) order.get("orderAmount"));
                o.setPaymentMethod((String) order.get("paymentMethod"));
                o.setAddress((String) order.get("address"));

                Integer orderId = (Integer) order.get("orderId");
                ArrayList<Map<String, Object>> orderItems = databasePersistence.loadData("Select * from OrderItem where orderId=" + orderId, new ArrayList<>());
                ArrayList<Product> product = new ArrayList<>();
                for(Map<String, Object> orderItem: orderItems){
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
                orderList.add(o);
            }
            return orderList;
        }catch(Exception e){
            return null;
        }
    }
}

