package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class RegisterSuccessState extends State {

    public RegisterSuccessState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Registered Successfully";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegisterSuccess";
    }

}