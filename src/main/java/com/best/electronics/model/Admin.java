package com.best.electronics.model;

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
}
