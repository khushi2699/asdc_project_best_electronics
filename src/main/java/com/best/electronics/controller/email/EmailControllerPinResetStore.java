package com.best.electronics.controller.email;

import com.best.electronics.model.User;

public interface EmailControllerPinResetStore {

    public void storePinToDB(int token, String email);

    public boolean checkCombination(int token, String email);

    public void storeNewPassword(String password, String confirmPassword, String email);
}
