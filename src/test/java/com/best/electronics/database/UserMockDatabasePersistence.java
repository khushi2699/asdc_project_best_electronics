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
    public ArrayList<Map<String, Object>> loadData(String query){
        if(query.equals("{call get_user_login_details()}")){
            ArrayList<Map<String, Object>> dataList = new ArrayList<>();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("emailAddress", "p@gmail.com");
            dataMap.put("password", "d1b2547eec96cabc2d4ab655cd1b1ba9");
            dataList.add(dataMap);
            return dataList;
        } else if(query.equals("{call get_user_emailAddress()}")){
            ArrayList<Map<String, Object>> dataList = new ArrayList<>();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("emailAddress", "p@gmail.com");
            dataList.add(dataMap);
            return dataList;
        }
        return null;
    }
}
