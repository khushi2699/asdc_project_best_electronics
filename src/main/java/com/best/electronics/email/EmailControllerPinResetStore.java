package com.best.electronics.email;

import java.security.NoSuchAlgorithmException;

public interface EmailControllerPinResetStore {

    public boolean storePinToDB(Integer token, String email,String type);

}
