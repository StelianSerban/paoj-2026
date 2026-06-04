package com.pao.proiect.catalog.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Properties props = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (is == null) {
                throw new RuntimeException("db.properties nu a fost gasit in resources/");
            }
            props.load(is);

            String url  = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("[DB] Conexiune stabilita: " + url);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Eroare la initializarea conexiunii DB: " + e.getMessage(), e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.connection.isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Conexiune inchisa.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Eroare la inchidere: " + e.getMessage());
        }
    }
}