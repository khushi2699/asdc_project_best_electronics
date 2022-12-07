package com.best.electronics.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySQLDatabasePersistence implements IDatabasePersistence{

    @Override
    public ArrayList<Map<String, Object>> loadData(String query) throws Exception {
        Connection conn = null;
        CallableStatement smt = null;
        ArrayList<Map<String, Object>> result= new ArrayList<>();
        try{
            DatabaseConnection dbConnectionInstance = DatabaseConnection.getInstance();
            conn = dbConnectionInstance.getDBConnection();
            if (conn != null) {
                smt = conn.prepareCall(query);
                boolean hadResults = smt.execute();

                while(hadResults){
                    ResultSet rs = smt.executeQuery();
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();
                    while(rs.next()){
                        Map<String, Object> map = new HashMap<>();
                        for(int j=1; j<cols+1; j++){
                            map.put(meta.getColumnName(j), rs.getObject(j));
                            System.out.println(meta.getColumnName(j) + " : " + rs.getObject(j));
                        }
                        result.add(map);
                    }
                    hadResults = smt.getMoreResults();
                }
            }
        }catch (Exception e){
            System.out.println("Exception occurred while executing query: " + query);
        } finally{
            smt.close();
            conn.close();
        }
        return result;
    }

    @Override
    public Boolean saveData(String query, ArrayList<Object> parameters) throws Exception {
        Connection conn = null;
        CallableStatement smt = null;
        try{
            DatabaseConnection dbConnectionInstance = DatabaseConnection.getInstance();
            conn = dbConnectionInstance.getDBConnection();
            if (conn != null) {
                smt = conn.prepareCall(query);

                int i = 0;
                for(Object parameter: parameters) {
                    smt.setObject(++i, parameter);
                }
                smt.execute();
                return true;
            }
        }catch (Exception e){
            System.out.println("Exception occurred while executing query: " + e.getMessage());
            return false;
        } finally{
            smt.close();
            conn.close();
        }
        return false;
    }
}
