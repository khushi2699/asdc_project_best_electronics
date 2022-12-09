package com.best.electronics.database;
import com.best.electronics.properties.DatabaseProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection dbDataSource = null;
    private static BasicDataSource basicDataSource = null;

    public static DatabaseConnection getInstance(){
        if(dbDataSource == null){
            synchronized (DatabaseConnection.class){
                if(dbDataSource == null){
                    dbDataSource = new DatabaseConnection();
                }
            }
        }
        return dbDataSource;
    }

    private DatabaseConnection(){
        basicDataSource = new BasicDataSource();

        DatabaseProperties dbProperties = new DatabaseProperties();
        basicDataSource.setUrl(dbProperties.getUrl());
        basicDataSource.setUsername(dbProperties.getUsername());
        basicDataSource.setPassword(dbProperties.getPassword());
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(10);
        basicDataSource.setMaxOpenPreparedStatements(100);
    }

    public Connection getDBConnection() throws SQLException {
        basicDataSource.getNumActive();
        return basicDataSource.getConnection();
    }
}

