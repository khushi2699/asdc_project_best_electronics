package com.best.electronics.controller.email;

import com.best.electronics.model.User;

import java.security.NoSuchAlgorithmException;

public interface EmailControllerPinResetStore {

    public void storePinToDB(int token, String email);

    public boolean checkCombination(int token, String email);

    public String storeNewPassword(String password, String confirmPassword, String email) throws NoSuchAlgorithmException;
}
