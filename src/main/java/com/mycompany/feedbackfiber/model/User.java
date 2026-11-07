package com.mycompany.feedbackfiber.model;

import java.time.LocalDate;

public record User(String id, String nama, String role,
                   LocalDate tanggalLahir, String gender, String domisili) {
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }
}
