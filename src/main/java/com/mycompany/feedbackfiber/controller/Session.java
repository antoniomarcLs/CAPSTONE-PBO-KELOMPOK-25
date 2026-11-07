package com.mycompany.feedbackfiber.controller;

import com.mycompany.feedbackfiber.model.AdminActivity;
import com.mycompany.feedbackfiber.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Session {

    private static User current;
    private static final List<AdminActivity> adminActivities = new ArrayList<>();

    private Session() {
    }

    public static synchronized void set(User u) {
        // Session baru selalu dimulai dengan histori aktivitas yang kosong.
        adminActivities.clear();
        current = u;
    }

    public static synchronized User user() {
        return current;
    }

    /** Catat aktivitas hanya untuk akun admin yang sedang aktif. */
    public static synchronized void catatAktivitasAdmin(String aksi, String judulKuesioner) {
        catatAktivitasAdmin(aksi, judulKuesioner, "");
    }

    public static synchronized void catatAktivitasAdmin(String aksi, String judulKuesioner, String detail) {
        if (current == null || !current.isAdmin()) return;
        adminActivities.add(new AdminActivity(aksi, judulKuesioner,
                detail == null ? "" : detail, LocalDateTime.now()));
    }

    /** Mengembalikan salinan agar daftar session tidak dapat diubah dari luar. */
    public static synchronized List<AdminActivity> historiAktivitasAdmin() {
        return List.copyOf(adminActivities);
    }

    public static synchronized void clear() {
        adminActivities.clear();
        current = null;
    }
}
