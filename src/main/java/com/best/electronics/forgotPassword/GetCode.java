package com.best.electronics.forgotPassword;

public interface GetCode {
    void generateCode(String type, String medium) throws Exception;
}
