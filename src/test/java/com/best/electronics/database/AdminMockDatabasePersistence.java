package com.best.electronics.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminMockDatabasePersistence implements IDatabasePersistence{

    @Override
    public Boolean saveData(String query, ArrayList<Object> parameters) {
        return null;
    }

    @Override
    public ArrayList<Map<String, Object>> loadData(String query) {
        if(query.equals("{call get_admin_login_details()}")){
            ArrayList<Map<String, Object>> dataList = new ArrayList<>();
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("emailAddress", "admin@gmail.com");
            dataMap.put("password", "d1b2547eec96cabc2d4ab655cd1b1ba9");
            dataList.add(dataMap);
            return dataList;
        }
        return null;
    }
}
