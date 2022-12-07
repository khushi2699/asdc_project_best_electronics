package com.best.electronics.login;

public class InvalidEmailAddressState extends LoginState{

    InvalidEmailAddressState(String type) {
        super(type);
    }

    @Override
    public void setLoginStatus() {
        loginStatus = "EmailAddress does not Exists!";
    }

    @Override
    public void setNextPage(String type) {
        if("admin".equalsIgnoreCase(type)){
            nextPage = "adminLogin.html";
        }else{
            nextPage = "userLogin.html";
        }
    }
}
