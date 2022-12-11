package com.best.electronics.model;

public abstract class Account {

    public abstract void setAccountId(Integer id);

    public abstract Integer getAccountId();

    public abstract String getFirstName();

    public abstract void setFirstName(String firstName);

    public abstract String getLastName();

    public abstract void setLastName(String lastName);

    public abstract void setEmailAddress(String email);

    public abstract String getEmailAddress();

    public abstract void setPassword(String password);

    public abstract String getPassword();

    public abstract String getConfirmPassword();

    public abstract void setConfirmPassword(String confirmPassword);

    public abstract Integer getToken();

    public abstract void setToken(Integer token);
}
