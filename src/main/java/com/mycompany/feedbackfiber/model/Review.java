package com.mycompany.feedbackfiber.model;

public record Review(String id, boolean anonim, String isi, int upvote, String idUser, String nama) {

    public String penulis() {
        return anonim ? "Anonim" : nama;
    }
}
