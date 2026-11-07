package com.mycompany.feedbackfiber.model;

import java.time.LocalDate;

public record HistoriKuesioner(
        String id,
        String judul,
        LocalDate tanggal,
        int jumlahJawaban,
        double rataRata
) {
}
