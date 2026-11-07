package com.mycompany.feedbackfiber.model;

import java.time.LocalDateTime;

/** Aktivitas admin selama sesi login aktif. */
public record AdminActivity(String aksi, String judulKuesioner, String detail, LocalDateTime waktu) {
}
