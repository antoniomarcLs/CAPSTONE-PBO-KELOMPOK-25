package com.mycompany.feedbackfiber.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Satu-satunya tempat aturan validasi data (format, panjang, dsb) didefinisikan.
 * Controller memakai kelas ini sebagai penjaga utama sebelum data disimpan.
 * View boleh memakainya juga untuk memberi umpan balik cepat ke pengguna,
 * tetapi tidak boleh menjadi satu-satunya penjagaan.
 */
public final class Validator {

    public static final int MIN_PASSWORD = 8;
    public static final int MIN_USIA = 12;
    /** Tahun lahir paling awal yang diterima (batas bawah kalender pada pemilih tanggal). */
    public static final int MIN_TAHUN_LAHIR = 1900;

    public static final Pattern USERNAME = Pattern.compile("^[A-Za-z][A-Za-z0-9._]{2,49}$");
    public static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    public static final Pattern TELEPON = Pattern.compile("^(\\+62|62|0)8[0-9]{7,11}$");

    private Validator() {
    }

    public static boolean namaValid(String nama) {
        return nama != null && !nama.isBlank() && nama.trim().length() <= 100;
    }

    public static boolean tanggalLahirValid(LocalDate lahir) {
        return lahir != null && lahir.isBefore(LocalDate.now())
                && !lahir.isBefore(tanggalLahirTerlama());
    }

    /** Tanggal lahir paling awal yang diizinkan (1 Januari MIN_TAHUN_LAHIR). */
    public static LocalDate tanggalLahirTerlama() {
        return LocalDate.of(MIN_TAHUN_LAHIR, 1, 1);
    }

    /** Tanggal lahir paling akhir yang diizinkan: hari ini (waktu komputer) dikurangi MIN_USIA tahun. */
    public static LocalDate tanggalLahirTerakhir() {
        return LocalDate.now().minusYears(MIN_USIA);
    }

    /** Usia minimal terpenuhi bila ulang tahun ke-MIN_USIA sudah tercapai pada hari ini. */
    public static boolean usiaMinimalTerpenuhi(LocalDate lahir) {
        return lahir != null && !lahir.isAfter(tanggalLahirTerakhir());
    }

    public static boolean genderValid(String gender) {
        return gender != null && !gender.isBlank();
    }

    public static boolean usernameValid(String ident) {
        return ident != null && USERNAME.matcher(ident).matches();
    }

    public static boolean emailValid(String ident) {
        return ident != null && ident.length() <= 100 && EMAIL.matcher(ident).matches();
    }

    public static boolean teleponValid(String ident) {
        return ident != null && normalisasiTelepon(ident) != null;
    }

    public static boolean domisiliValid(String domisili) {
        return domisili != null && !domisili.isBlank() && domisili.length() <= 255;
    }

    public static boolean passwordValid(String password) {
        return password != null && password.length() >= MIN_PASSWORD;
    }

    public static String normalisasiTelepon(String s) {
        if (s == null) {
            return null;
        }
        String t = s.replaceAll("[\\s-]", "");
        if (!TELEPON.matcher(t).matches()) {
            return null;
        }
        if (t.startsWith("+62")) {
            return "0" + t.substring(3);
        }
        if (t.startsWith("62")) {
            return "0" + t.substring(2);
        }
        return t;
    }
}
