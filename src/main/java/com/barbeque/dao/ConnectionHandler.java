package com.barbeque.dao;

import com.barbeque.config.ConfigProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private String url;
    private String driver;
    private String userName;
    private String password;

    public ConnectionHandler() {
        String host = ConfigProperties.db_host;
        String port = ConfigProperties.db_port;
        String dbName = ConfigProperties.db_name;
        userName = ConfigProperties.db_username;
        password = ConfigProperties.db_password;
        url = "jdbc:mysql://"+host+":"+port+"/" + dbName;
        System.out.println("DBURL: " + url);
        driver = "com.mysql.jdbc.Driver";
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
