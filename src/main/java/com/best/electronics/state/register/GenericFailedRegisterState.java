package com.best.electronics.state.register;

import com.best.electronics.state.State;

public class GenericFailedRegisterState extends State {

    public GenericFailedRegisterState(String type) {
        super(type);
    }

    @Override
    public void setStatus() {
        status = "Unexpected exception occurred! Please try again!";
    }

    @Override
    public void setNextPage(String type) {
        nextPage = type + "RegistrationForm";
    }
}
