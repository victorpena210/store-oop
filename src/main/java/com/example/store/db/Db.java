package com.example.store.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Db {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Db.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties not found in classpath (src/main/resources).");
            }
            PROPS.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            // Optional but harmless to be explicit:
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = envOrProp("STORE_DB_URL", "db.url");
            String user = envOrProp("STORE_DB_USER", "db.username");
            String pass = envOrProp("STORE_DB_PASSWORD", "db.password");

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed: " + e.getMessage(), e);
        }
    }

    private static String envOrProp(String envKey, String propKey) {
        String envVal = System.getenv(envKey);
        return (envVal != null && !envVal.isBlank()) ? envVal : PROPS.getProperty(propKey);
    }
}
