package com.mycompany.feedbackfiber.controller;

import com.mycompany.feedbackfiber.model.Kuesioner;
import com.mycompany.feedbackfiber.model.KuesionerModel;
import com.mycompany.feedbackfiber.model.HistoriKuesioner;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class KuesionerController {

    private final KuesionerModel model = new KuesionerModel();

    // ---- umum / Admin ----

    public List<Kuesioner> daftar() throws SQLException {
        return model.findAll();
    }

    public Optional<Kuesioner> cari(String id) throws SQLException {
        return id == null || id.isBlank() ? Optional.empty() : model.findById(id.trim());
    }

    public boolean punyaJawaban(String id) throws SQLException {
        return model.hasJawaban(id);
    }

    public static List<String> parsePertanyaan(String teks) {
        List<String> out = new ArrayList<>();
        for (String line : teks.split("\\R")) {
            String t = line.trim();
            if (!t.isEmpty()) {
                out.add(t);
            }
        }
        return out;
    }

    public String validasi(String judul, String deskripsi, List<String> pertanyaan, boolean strukturTerkunci) {
        if (judul.isBlank()) {
            return "Judul wajib diisi.";
        }
        if (judul.trim().length() > 255) {
            return "Judul maksimal 255 karakter.";
        }
        if (deskripsi.isBlank()) {
            return "Deskripsi wajib diisi.";
        }
        if (deskripsi.trim().length() > 255) {
            return "Deskripsi maksimal 255 karakter.";
        }
        if (!strukturTerkunci) {
            if (pertanyaan.isEmpty()) {
                return "Minimal satu pertanyaan harus diisi.";
            }
            for (String p : pertanyaan) {
                if (p.length() > 255) {
                    return "Setiap pertanyaan maksimal 255 karakter.";
                }
            }
        }
        return null;
    }

    public String buat(String judul, String deskripsi, String jenis, List<String> pertanyaan) throws SQLException {
        String id = model.create(judul.trim(), deskripsi.trim(), jenis, pertanyaan);
        StringBuilder detail = new StringBuilder("ID Kuesioner: ").append(id)
                .append("\nDeskripsi: ").append(deskripsi.trim()).append("\nPertanyaan:");
        int n = 1;
        for (String p : pertanyaan) detail.append("\n").append(n++).append(". ").append(p);
        Session.catatAktivitasAdmin("Kuesioner dibuat", judul.trim(), detail.toString());
        return id;
    }

    public void ubah(String id, String judul, String deskripsi, String jenis, List<String> pertanyaan)
            throws SQLException {
        Kuesioner sebelum = model.findById(id).orElse(null);
        model.update(id, judul.trim(), deskripsi.trim(), pertanyaan);
        Kuesioner sesudah = model.findById(id).orElse(null);
        StringBuilder detail = new StringBuilder();
        if (sebelum != null && sesudah != null) {
            if (!sebelum.judul().equals(sesudah.judul()))
                detail.append("Judul: \"").append(sebelum.judul()).append("\" → \"").append(sesudah.judul()).append("\"\n");
            if (!sebelum.deskripsi().equals(sesudah.deskripsi()))
                detail.append("Deskripsi: \"").append(sebelum.deskripsi()).append("\" → \"").append(sesudah.deskripsi()).append("\"\n");
            if (pertanyaan != null) {
                detail.append("Perubahan daftar pertanyaan (sebelum → sesudah):\\n");
                int jumlah = Math.max(sebelum.pertanyaan().size(), sesudah.pertanyaan().size());
                for (int i = 0; i < jumlah; i++) {
                    String lama = i < sebelum.pertanyaan().size() ? sebelum.pertanyaan().get(i).isi() : "[tidak ada]";
                    String baru = i < sesudah.pertanyaan().size() ? sesudah.pertanyaan().get(i).isi() : "[dihapus]";
                    if (!lama.equals(baru)) {
                        detail.append("Pertanyaan ").append(i + 1).append(":\\n  Sebelum: ")
                                .append(lama).append("\\n  Sesudah: ").append(baru).append("\\n");
                    }
                }
                detail.append("Total pertanyaan: ").append(sebelum.pertanyaan().size())
                        .append(" → ").append(sesudah.pertanyaan().size());
            } else {
                detail.append("Struktur pertanyaan tidak diubah karena kuesioner telah memiliki jawaban.\n");
            }
        }
        Session.catatAktivitasAdmin("Kuesioner diubah",
                sesudah == null ? judul.trim() : sesudah.judul(),
                detail.toString().isBlank() ? "Tidak ada perubahan nilai." : detail.toString());
    }

    public void hapus(String id) throws SQLException {
        Kuesioner k = model.findById(id).orElse(null);
        String judul = k == null ? id : k.judul();
        StringBuilder detail = new StringBuilder("ID Kuesioner: ").append(id)
                .append("\nJudul: ").append(judul);
        if (k != null) {
            detail.append("\nDeskripsi: ").append(k.deskripsi()).append("\nPertanyaan yang dihapus:");
            int n = 1;
            for (var p : k.pertanyaan()) detail.append("\n").append(n++).append(". ").append(p.isi());
        }
        model.delete(id);
        Session.catatAktivitasAdmin("Kuesioner dihapus", judul, detail.toString());
    }

    // ---- User ----

    public List<HistoriKuesioner> historiSaya() throws SQLException {
        return model.findHistoryByUser(Session.user().id());
    }

    public boolean sudahDiisi(String idKuesioner) throws SQLException {
        return model.sudahDiisi(Session.user().id(), idKuesioner);
    }

    public void simpanJawaban(Map<String, String> jawaban) throws SQLException {
        model.simpanJawaban(Session.user().id(), new LinkedHashMap<>(jawaban));
    }
}
