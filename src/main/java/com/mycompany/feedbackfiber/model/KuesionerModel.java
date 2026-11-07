package com.mycompany.feedbackfiber.model;

import com.mycompany.feedbackfiber.config.Koneksi;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KuesionerModel {


    public List<HistoriKuesioner> findHistoryByUser(String idUser) throws SQLException {
        String sql = "SELECT k.id_kuesioner, k.judul, MAX(j.tanggal_pengisian) AS tanggal, "
                + "COUNT(j.id_jawaban) AS jumlah_jawaban, AVG(CAST(j.point AS DECIMAL(10,2))) AS rata_rata "
                + "FROM jawaban j "
                + "JOIN pertanyaan p ON p.id_pertanyaan = j.id_pertanyaan "
                + "JOIN kuesioner k ON k.id_kuesioner = p.id_kuesioner "
                + "WHERE j.id_user = ? "
                + "GROUP BY k.id_kuesioner, k.judul "
                + "ORDER BY MAX(j.tanggal_pengisian) DESC";
        List<HistoriKuesioner> out = new ArrayList<>();
        try (Connection c = Koneksi.open();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date tanggal = rs.getDate("tanggal");
                    out.add(new HistoriKuesioner(
                            rs.getString("id_kuesioner"),
                            rs.getString("judul"),
                            tanggal == null ? null : tanggal.toLocalDate(),
                            rs.getInt("jumlah_jawaban"),
                            rs.getDouble("rata_rata")
                    ));
                }
            }
        }
        return out;
    }

        public List<Kuesioner> findAll() throws SQLException {
        String sql = "SELECT k.id_kuesioner, k.judul, k.deskripsi, "
            + "(SELECT COUNT(*) FROM pertanyaan p "
            + "WHERE p.id_kuesioner = k.id_kuesioner) AS jumlah_pertanyaan "
            + "FROM kuesioner k "
            + "ORDER BY CAST(SUBSTRING(k.id_kuesioner,2) AS UNSIGNED)";
    List<Kuesioner> out = new ArrayList<>();
    try (Connection c = Koneksi.open();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            out.add(new Kuesioner(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    Kuesioner.SKALA,
                    rs.getInt(4),
                    List.of()
            ));
        }
    }
    return out;
    }

    public Optional<Kuesioner> findById(String id) throws SQLException {
        try (Connection c = Koneksi.open()) {
            Kuesioner head;
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id_kuesioner, judul, deskripsi FROM kuesioner WHERE id_kuesioner = ?")) {
                ps.setString(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return Optional.empty();
                    }
                        head = new Kuesioner(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    Kuesioner.SKALA,
                    0,
                    List.of()
                    );  
                }
            }
            List<Pertanyaan> list = new ArrayList<>();
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id_pertanyaan, isi_pertanyaan FROM pertanyaan WHERE id_kuesioner = ? "
                    + "ORDER BY CAST(SUBSTRING(id_pertanyaan,2) AS UNSIGNED)")) {
                ps.setString(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(new Pertanyaan(rs.getString(1), rs.getString(2)));
                    }
                }
            }
            return Optional.of(new Kuesioner(head.id(), head.judul(), head.deskripsi(),
                    head.jenisJawaban(), list.size(), list));
        }
    }

    public String create(String judul, String deskripsi, String jenis, List<String> pertanyaan) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                String id = IdGenerator.format("K", IdGenerator.max(c, "kuesioner", "id_kuesioner", "K") + 1);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO kuesioner (id_kuesioner, judul, deskripsi) VALUES (?,?,?)")) {
                    ps.setString(1, id);
                    ps.setString(2, judul);
                    ps.setString(3, deskripsi);
                    ps.executeUpdate();
                }
                insertPertanyaan(c, id, pertanyaan);
                c.commit();
                return id;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public void update(String id, String judul, String deskripsi, List<String> pertanyaan)
            throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                if (hasJawaban(c, id)) {
                    try (PreparedStatement ps = c.prepareStatement(
                            "UPDATE kuesioner SET judul = ?, deskripsi = ? WHERE id_kuesioner = ?")) {
                        ps.setString(1, judul);
                        ps.setString(2, deskripsi);
                        ps.setString(3, id);
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = c.prepareStatement(
                            "UPDATE kuesioner SET judul = ?, deskripsi = ? WHERE id_kuesioner = ?")) {
                        ps.setString(1, judul);
                        ps.setString(2, deskripsi);
                        ps.setString(3, id);
                        ps.executeUpdate();
                    }
                    if (pertanyaan != null) {
                        exec(c, "DELETE FROM pertanyaan WHERE id_kuesioner = ?", id);
                        insertPertanyaan(c, id, pertanyaan);
                    }
                }
                c.commit();
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
                exec(c, "DELETE FROM jawaban WHERE id_pertanyaan IN "
                        + "(SELECT id_pertanyaan FROM pertanyaan WHERE id_kuesioner = ?)", id);
                exec(c, "DELETE FROM pertanyaan WHERE id_kuesioner = ?", id);
                exec(c, "DELETE FROM kuesioner WHERE id_kuesioner = ?", id);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public boolean hasJawaban(String idKuesioner) throws SQLException {
        try (Connection c = Koneksi.open()) {
            return hasJawaban(c, idKuesioner);
        }
    }

    public boolean sudahDiisi(String idUser, String idKuesioner) throws SQLException {
        String sql = "SELECT 1 FROM jawaban j JOIN pertanyaan p ON p.id_pertanyaan = j.id_pertanyaan "
                + "WHERE j.id_user = ? AND p.id_kuesioner = ? LIMIT 1";
        try (Connection c = Koneksi.open(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, idUser);
            ps.setString(2, idKuesioner);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void simpanJawaban(String idUser, Map<String, String> jawabanPerPertanyaan) throws SQLException {
        try (Connection c = Koneksi.open()) {
            c.setAutoCommit(false);
            try {
                int n = IdGenerator.max(c, "jawaban", "id_jawaban", "J");
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO jawaban (id_jawaban, point, tanggal_pengisian, id_user, id_pertanyaan) "
                        + "VALUES (?,?,?,?,?)")) {
                    for (Map.Entry<String, String> e : jawabanPerPertanyaan.entrySet()) {
                        ps.setString(1, IdGenerator.format("J", ++n));
                        ps.setString(2, e.getValue());
                        ps.setDate(3, Date.valueOf(LocalDate.now()));
                        ps.setString(4, idUser);
                        ps.setString(5, e.getKey());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }


    private boolean hasJawaban(Connection c, String id) throws SQLException {
        String sql = "SELECT 1 FROM jawaban j JOIN pertanyaan p ON p.id_pertanyaan = j.id_pertanyaan "
                + "WHERE p.id_kuesioner = ? LIMIT 1";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void insertPertanyaan(Connection c, String idKuesioner, List<String> list) throws SQLException {
        int n = IdGenerator.max(c, "pertanyaan", "id_pertanyaan", "P");
        try (PreparedStatement ps = c.prepareStatement(
                "INSERT INTO pertanyaan (id_pertanyaan, isi_pertanyaan, id_kuesioner) VALUES (?,?,?)")) {
            for (String isi : list) {
                ps.setString(1, IdGenerator.format("P", ++n));
                ps.setString(2, isi);
                ps.setString(3, idKuesioner);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void exec(Connection c, String sql, String param) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, param);
            ps.executeUpdate();
        }
    }
}
