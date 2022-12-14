package com.best.electronics.repository;

import com.best.electronics.database.IDatabasePersistence;
import com.best.electronics.model.Admin;
import com.best.electronics.model.Order;
import com.best.electronics.model.Product;
import com.best.electronics.model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRepository {

    private final IDatabasePersistence databasePersistence;

    public AdminRepository(IDatabasePersistence databasePersistence) {
        this.databasePersistence = databasePersistence;
    }

    public ArrayList<Map<String, Object>> getAllAdmins(Integer id){
        ArrayList<Map<String, Object>> adminList = new ArrayList<>();
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(id);
            adminList =  databasePersistence.loadData("{call get_all_admins(?)}", parameters);
            return adminList;
        }catch(Exception e){
            return adminList;
        }
    }

    public Map<String, Object> getAdminDetails(Integer adminId){
        try{
            ArrayList<Object> parameters = new ArrayList<>();
            parameters.add(adminId);
            ArrayList<Map<String, Object>> adminDetails = databasePersistence.loadData("{call get_admin_details(?)}", parameters);
            return adminDetails.get(0);
        }catch(Exception e){
            return null;
        }
    }
    public ArrayList<Order> getOrderDetails() {
        ArrayList<Order> orderList = new ArrayList<>();
        try {
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
            return orderList;
        }
    }

    private Boolean isUsernameValid(String name) {
        String urlPattern = "^[a-zA-Z]{2,20}$";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
    public String updateAdminDetails(Admin admin){
        try{
            ArrayList<Object> updatedDetails = new ArrayList<>();

            if(isUsernameValid(admin.getFirstName()) && isUsernameValid(admin.getLastName())){
                updatedDetails.add(admin.getEmailAddress());
                updatedDetails.add(admin.getFirstName());
                updatedDetails.add(admin.getLastName());
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

    public Boolean saveAdminData(ArrayList<Object> params) throws Exception {
        return databasePersistence.saveData("{call create_admin(?, ?, ?, ?, ?)}", params);
    }

    public Boolean deleteAdmin(Integer adminId){
        ArrayList<Object> params = new ArrayList<>();
        params.add(adminId);
        try{
            return databasePersistence.saveData("{call delete_admin(?)}", params);
        }catch (Exception e){
            return false;
        }
    }
}
