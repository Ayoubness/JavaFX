package com.ezzariy.dao;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionFactory {

    public static final String URL = "jdbc:mysql://localhost:3306/javafxtps?createDatabaseIfNotExist=true&useSSL=false";
    public static final String USER = "root";
    public static final String PASS = "";

    public static Connection connection = null;

    public ConnectionFactory() {
        init();
    }

    public static Connection getConnection() {
        if (Objects.isNull(connection))
            new ConnectionFactory();
        return connection;
    }

    public void init() {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }
}
