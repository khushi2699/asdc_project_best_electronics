package com.best.electronics.email;

import com.best.electronics.model.Product;

import java.util.ArrayList;

public interface ISendOrderStatusEmail {
    Boolean sendEmail(Integer orderId, Double orderAmount, String orderDateString, String emailAddress, String orderStatus, ArrayList<Product> products);
}
