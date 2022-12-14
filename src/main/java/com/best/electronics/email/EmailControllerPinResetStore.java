package com.best.electronics.email;

public interface EmailControllerPinResetStore {

    boolean storePinToDB(Integer token, String email,String type);

}
