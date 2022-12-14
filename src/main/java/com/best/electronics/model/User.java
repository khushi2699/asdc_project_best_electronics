package com.best.electronics.model;

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

    @Override
    public String getGender() {
        return gender;
    }

    @Override
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
}