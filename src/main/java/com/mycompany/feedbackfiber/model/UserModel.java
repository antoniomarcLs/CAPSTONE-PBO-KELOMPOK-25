package com.mycompany.feedbackfiber.model;

import com.mycompany.feedbackfiber.config.Koneksi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserModel {

    public record Credential(User user, String password) {
    }

    public Credential findByIdentity(String raw, String phone) throws SQLException {
        String sql = "SELECT u.id_user, u.nama_pengguna, u.role, u.password, u.tanggal_lahir, u.gender, u.domisili FROM user u "
                + "LEFT JOIN user_account ua ON ua.id_user = u.id_user "
                + "LEFT JOIN user_email ue ON ue.id_user = u.id_user "
                + "LEFT JOIN user_phone up ON up.id_user = u.id_user "
                + "WHERE ua.username = ? OR ue.email = ? OR up.nomor_hp = ? LIMIT 1";
        try (Connection c = Koneksi.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, raw);
            ps.setString(2, raw);
            ps.setString(3, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Date tanggalLahir = rs.getDate("tanggal_lahir");
                User u = new User(rs.getString("id_user"), rs.getString("nama_pengguna"),
                        rs.getString("role"), tanggalLahir == null ? null : tanggalLahir.toLocalDate(),
                        rs.getString("gender"), rs.getString("domisili"));
                return new Credential(u, rs.getString(4));
            }
        }
    }

    public boolean isIdentityUsed(IdentityMethod m, String identity) throws SQLException {
        String sql = "SELECT 1 FROM " + m.table + " WHERE " + m.column + " = ?";
        try (Connection c = Koneksi.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, identity);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public String register(String nama, LocalDate lahir, String gender, IdentityMethod m,
                           String identity, String password, String domisili) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                String id = IdGenerator.format("U", IdGenerator.max(c, "user", "id_user", "U") + 1);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO user (id_user, nama_pengguna, tanggal_lahir, gender, password, domisili, role) "
                        + "VALUES (?,?,?,?,?,?,'user')")) {
                    ps.setString(1, id);
                    ps.setString(2, nama);
                    ps.setDate(3, Date.valueOf(lahir));
                    ps.setString(4, gender);
                    ps.setString(5, password);
                    ps.setString(6, domisili);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO " + m.table + " (id_user, " + m.column + ") VALUES (?,?)")) {
                    ps.setString(1, id);
                    ps.setString(2, identity);
                    ps.executeUpdate();
                }
                c.commit();
                return id;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }
}
