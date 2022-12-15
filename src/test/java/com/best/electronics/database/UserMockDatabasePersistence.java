package com.best.electronics.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserMockDatabasePersistence implements IDatabasePersistence{

    @Override
    public Boolean saveData(String query, ArrayList<Object> parameters){
        return null;
    }

    @Override
    public ArrayList<Map<String, Object>> loadData(String query, ArrayList<Object> parameters){
        ArrayList<Map<String, Object>> dataList = new ArrayList<>();
        HashMap<String, Object> dataMap = new HashMap<>();
        switch (query) {
            case "{call get_user_login_details()}":
                dataMap.put("id", 1);
                dataMap.put("emailAddress", "p@gmail.com");
                dataMap.put("password", "d1b2547eec96cabc2d4ab655cd1b1ba9");
                dataList.add(dataMap);
                break;
            case "{call get_user_emailAddress()}":
                dataMap.put("emailAddress", "p@gmail.com");
                dataList.add(dataMap);
                break;
            case "{call get_all_user_details()}":
                dataMap.put("userId", 1);
                dataMap.put("firstName", "Test");
                dataMap.put("lastName", "User");
                dataMap.put("dob", "12/12/1998");
                dataMap.put("gender", "Female");
                dataMap.put("address", "Canada");
                dataMap.put("emailAddress", "p@gmail.com");
                dataMap.put("password", "d1b2547eec96cabc2d4ab655cd1b1ba9");
                dataList.add(dataMap);
                break;
        }
        return dataList;
    }
}
