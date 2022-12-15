package com.best.electronics.model;

public class CardDetails {

    private String cardName;
    private String cardType;
    private String cardNumber;
    private String expiryDate;
    private String securityCode;
    private Integer userId;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
