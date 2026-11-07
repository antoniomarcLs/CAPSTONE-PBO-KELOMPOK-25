package com.mycompany.feedbackfiber.controller;

import com.mycompany.feedbackfiber.model.IdentityMethod;
import com.mycompany.feedbackfiber.model.User;
import com.mycompany.feedbackfiber.model.UserModel;
import com.mycompany.feedbackfiber.model.Validator;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;

public class AuthController {

    private final UserModel users = new UserModel();

    public enum LoginStatus { OK, BELUM_TERDAFTAR, PASSWORD_SALAH }

    public record LoginResult(LoginStatus status, User user) {
    }

    public enum RegisterStatus { OK, DATA_TIDAK_VALID, IDENTITAS_DIGUNAKAN }

    public record RegisterResult(RegisterStatus status, String pesan) {
    }


    public LoginResult login(String identitas, String password) throws SQLException {
        String id = identitas.trim();
        String phone = Validator.normalisasiTelepon(id);
        UserModel.Credential c = users.findByIdentity(id, phone != null ? phone : id);
        if (c == null) {
            return new LoginResult(LoginStatus.BELUM_TERDAFTAR, null);
        }

        if (!c.password().equals(password)) {
            return new LoginResult(LoginStatus.PASSWORD_SALAH, null);
        }
        return new LoginResult(LoginStatus.OK, c.user());
    }


    public RegisterResult register(String nama, LocalDate lahir, String gender, IdentityMethod metode,
                                   String identitas, String password, String domisili) throws SQLException {
        String ident = identitas.trim();
        if (metode == IdentityMethod.EMAIL) {
            ident = ident.toLowerCase();
        } else if (metode == IdentityMethod.TELEPON) {
            String n = Validator.normalisasiTelepon(ident);
            ident = n != null ? n : ident;
        }

        String err = validasi(nama, lahir, gender, metode, ident, password, domisili);
        if (err != null) {
            return new RegisterResult(RegisterStatus.DATA_TIDAK_VALID, err);
        }
        if (users.isIdentityUsed(metode, ident)) {
            return new RegisterResult(RegisterStatus.IDENTITAS_DIGUNAKAN,
                    metode.fieldLabel + " tersebut sudah digunakan.");
        }
        try {
            users.register(nama.trim(), lahir, gender, metode, ident, password, domisili);
        } catch (SQLIntegrityConstraintViolationException dup) {
            return new RegisterResult(RegisterStatus.IDENTITAS_DIGUNAKAN,
                    metode.fieldLabel + " tersebut sudah digunakan.");
        }
        return new RegisterResult(RegisterStatus.OK, "Daftar Akun Berhasil");
    }

    /**
     * Penjagaan utama sebelum data disimpan ke database. Semua aturan format/panjang
     * diambil dari {@link Validator}, sehingga satu-satunya tempat aturan didefinisikan.
     */
    private String validasi(String nama, LocalDate lahir, String gender, IdentityMethod m,
                            String ident, String password, String domisili) {
        if (!Validator.namaValid(nama)) {
            return (nama == null || nama.isBlank()) ? "Nama wajib diisi." : "Nama maksimal 100 karakter.";
        }
        if (!Validator.tanggalLahirValid(lahir)) {
            return "Tanggal lahir tidak valid.";
        }
        if (!Validator.usiaMinimalTerpenuhi(lahir)) {
            return "Usia minimal untuk mendaftar adalah " + Validator.MIN_USIA + " tahun.";
        }
        if (!Validator.genderValid(gender)) {
            return "Gender wajib dipilih.";
        }
        if (ident.isEmpty()) {
            return m.fieldLabel + " wajib diisi.";
        }
        switch (m) {
            case AKUN_FIBER -> {
                if (!Validator.usernameValid(ident)) {
                    return "Username harus diawali huruf, 3-50 karakter, hanya huruf, angka, titik, atau underscore.";
                }
            }
            case EMAIL -> {
                if (!Validator.emailValid(ident)) {
                    return "Format email tidak valid.";
                }
            }
            case TELEPON -> {
                if (!Validator.teleponValid(ident)) {
                    return "Format nomor telepon tidak valid (contoh: 081234567890).";
                }
            }
        }
        if (!Validator.domisiliValid(domisili)) {
            return "Domisili wajib dipilih.";
        }
        if (!Validator.passwordValid(password)) {
            return "Password minimal " + Validator.MIN_PASSWORD + " karakter.";
        }
        return null;
    }
}
