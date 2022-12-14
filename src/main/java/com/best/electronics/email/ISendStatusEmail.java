package com.best.electronics.email;

import java.util.HashMap;

public interface ISendStatusEmail {

    Boolean sendEmail(String emailAddress, HashMap<String, Object> messageDetails);
}
