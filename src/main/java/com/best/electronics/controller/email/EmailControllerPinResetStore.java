package com.best.electronics.controller.email;

import java.security.NoSuchAlgorithmException;

public interface EmailControllerPinResetStore {

    public void storePinToDB(Integer token, String email);

    public boolean checkCombination(Integer token, String email);

    public String storeNewPassword(String password, String confirmPassword, String email) throws NoSuchAlgorithmException;
}
