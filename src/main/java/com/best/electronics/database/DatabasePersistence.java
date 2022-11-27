package com.best.electronics.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabasePersistence implements IDatabasePersistence{

    @Override
    public Integer saveData(String query) throws Exception {
        Connection conn = null;
        PreparedStatement smt = null;
        Integer success = -1;
        try{
            DatabaseConnection dbConnectionInstance = DatabaseConnection.getInstance();
            conn = dbConnectionInstance.getDBConnection();
            if (conn != null) {
                smt = conn.prepareStatement(query);
                return smt.executeUpdate();
            }
        } catch (Exception e){
            System.out.println("Exception occurred while executing query: " + query);
        }
        finally {
            smt.close();
            conn.close();
        }
        return success;
    }

    @Override
    public ArrayList<Map<String, Object>> loadData(String query) throws Exception {
        Connection conn = null;
        PreparedStatement smt = null;
        ArrayList<Map<String, Object>> result= new ArrayList<>();
        try{
            DatabaseConnection dbConnectionInstance = DatabaseConnection.getInstance();
            conn = dbConnectionInstance.getDBConnection();
            if (conn != null) {
                smt = conn.prepareStatement(query);
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
            }
        }catch (Exception e){
            System.out.println("Exception occurred while executing query: " + query);
        } finally{
            smt.close();
            conn.close();
        }
        return result;
    }
}
