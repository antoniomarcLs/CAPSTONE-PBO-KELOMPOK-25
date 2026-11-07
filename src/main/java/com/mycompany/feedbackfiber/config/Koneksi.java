package com.mycompany.feedbackfiber.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL = "jdbc:mariadb://localhost:3306/feedback_fiber_program_db";
    private static final String USER = "fiber_admin";
    private static final String PASSWORD = "123";

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
