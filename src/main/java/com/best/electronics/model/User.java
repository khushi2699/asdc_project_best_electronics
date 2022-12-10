package com.best.electronics.model;

import com.best.electronics.database.IDatabasePersistence;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String userId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private String reEnterPassword;

    private String dateOfBirth;

    private String gender;

    private String address;

    private Integer resetPasswordToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
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

    public Integer getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(Integer resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
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

}

