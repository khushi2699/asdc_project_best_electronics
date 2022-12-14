package com.best.electronics.email;

import java.security.NoSuchAlgorithmException;

public interface EmailControllerPinResetStore {

    public void storePinToDB(Integer token, String email);

}
