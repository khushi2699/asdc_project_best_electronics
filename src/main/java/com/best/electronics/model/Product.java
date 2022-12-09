package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;

public class Product {


    private String productId;

    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer productQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productName;

    private String productBrand;

    private String productDescription;

    private Double productPrice;

    private Integer availableQuantity;

    public Integer getUserQuantity() {
        return userQuantity;
    }

    public void setUserQuantity(Integer userQuantity) {
        this.userQuantity = userQuantity;
    }

    private Integer userQuantity;

    }

