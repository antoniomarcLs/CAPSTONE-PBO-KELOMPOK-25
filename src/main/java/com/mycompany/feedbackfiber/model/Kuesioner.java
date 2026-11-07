package com.mycompany.feedbackfiber.model;

import java.util.List;

public record Kuesioner(String id, String judul, String deskripsi, String jenisJawaban,
                        int jumlahPertanyaan, List<Pertanyaan> pertanyaan) {

    public static final String SKALA = "SKALA_1_5";

    public static String[] opsi(String jenis) {
        return new String[]{"1 - Sangat Tidak Puas", "2 - Tidak Puas", "3 - Cukup", "4 - Puas", "5 - Sangat Puas"};
    }

    public static String[] poin(String jenis) {
        return new String[]{"1", "2", "3", "4", "5"};
    }

    @Override
    public String toString() {
        return id + " - " + judul;
    }
}
