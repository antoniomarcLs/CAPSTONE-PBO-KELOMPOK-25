package com.mycompany.feedbackfiber.model;

import com.mycompany.feedbackfiber.config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewModel {

    private static final String SELECT = "SELECT r.id_review, r.anonim, r.review, r.upvote, r.id_user, "
            + "u.nama_pengguna FROM review r JOIN user u ON u.id_user = r.id_user ";

    public List<Review> findAll() throws SQLException {
        List<Review> out = new ArrayList<>();
        try (Connection c = Koneksi.open();
             PreparedStatement ps = c.prepareStatement(
                     SELECT + "ORDER BY CAST(SUBSTRING(r.id_review,2) AS UNSIGNED)");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(map(rs));
            }
        }
        return out;
    }

    public List<Review> findByUser(String idUser) throws SQLException {
        List<Review> out = new ArrayList<>();
        try (Connection c = Koneksi.open();
             PreparedStatement ps = c.prepareStatement(
                     SELECT + "WHERE r.id_user = ? ORDER BY CAST(SUBSTRING(r.id_review,2) AS UNSIGNED) DESC")) {
            ps.setString(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(map(rs));
                }
            }
        }
        return out;
    }

    public Optional<Review> findById(String id) throws SQLException {
        try (Connection c = Koneksi.open(); PreparedStatement ps = c.prepareStatement(SELECT + "WHERE r.id_review = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    public String insert(String idUser, String isi, boolean anonim) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                String id = IdGenerator.format("R", IdGenerator.max(c, "review", "id_review", "R") + 1);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO review (id_review, anonim, review, upvote, id_user) VALUES (?,?,?,0,?)")) {
                    ps.setString(1, id);
                    ps.setBoolean(2, anonim);
                    ps.setString(3, isi);
                    ps.setString(4, idUser);
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

    public void delete(String id) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                exec(c, "DELETE FROM upvote WHERE id_review = ?", id);
                exec(c, "DELETE FROM review WHERE id_review = ?", id);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public boolean sudahUpvote(String idReview, String idUser) throws SQLException {
        try (Connection c = Koneksi.open();
             PreparedStatement ps = c.prepareStatement("SELECT 1 FROM upvote WHERE id_review = ? AND id_user = ?")) {
            ps.setString(1, idReview);
            ps.setString(2, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void upvote(String idReview, String idUser) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                String id = IdGenerator.format("UV", IdGenerator.max(c, "upvote", "id_upvote", "UV") + 1);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO upvote (id_upvote, id_review, id_user) VALUES (?,?,?)")) {
                    ps.setString(1, id);
                    ps.setString(2, idReview);
                    ps.setString(3, idUser);
                    ps.executeUpdate();
                }
                sinkronkanHitungan(c, idReview);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public void unvote(String idReview, String idUser) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(
                        "DELETE FROM upvote WHERE id_review = ? AND id_user = ?")) {
                    ps.setString(1, idReview);
                    ps.setString(2, idUser);
                    ps.executeUpdate();
                }
                sinkronkanHitungan(c, idReview);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    private void sinkronkanHitungan(Connection c, String idReview) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(
                "UPDATE review SET upvote = (SELECT COUNT(*) FROM upvote WHERE id_review = ?) WHERE id_review = ?")) {
            ps.setString(1, idReview);
            ps.setString(2, idReview);
            ps.executeUpdate();
        }
    }

    private void exec(Connection c, String sql, String param) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            ps.executeUpdate();
        }
    }

    private Review map(ResultSet rs) throws SQLException {
        return new Review(rs.getString(1), rs.getBoolean(2), rs.getString(3), rs.getInt(4),
                rs.getString(5), rs.getString(6));
    }
}
