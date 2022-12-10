package com.best.electronics.database;

import java.util.ArrayList;
import java.util.Map;

public interface IDatabasePersistence {

    Boolean saveData(String query, ArrayList<Object> parameters) throws Exception;

    ArrayList<Map<String, Object>> loadData(String query, ArrayList<Object> parameters) throws Exception;
}
