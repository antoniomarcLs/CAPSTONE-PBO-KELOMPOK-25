package com.mycompany.feedbackfiber.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class IdGenerator {

    private IdGenerator() {
    }

    public static int max(Connection c, String table, String col, String prefix) throws SQLException {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(" + col + "," + (prefix.length() + 1)
                + ") AS UNSIGNED)),0) FROM " + table + " WHERE " + col + " LIKE ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public static String format(String prefix, int n) {
        return prefix + String.format("%03d", n);
    }
}
