package com.github.aelmod.adadm.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    final static String dbUrl = "";
    final static String user = "";
    final static String pass = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, user, pass);
    }
}
