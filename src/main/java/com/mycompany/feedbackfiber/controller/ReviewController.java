package com.mycompany.feedbackfiber.controller;

import com.mycompany.feedbackfiber.model.Review;
import com.mycompany.feedbackfiber.model.ReviewModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewController {

    public static final int MAX_REVIEW = 2000;

    private final ReviewModel model = new ReviewModel();

    public List<Review> daftar() throws SQLException {
        return model.findAll();
    }

    public List<Review> historiSaya() throws SQLException {
        return model.findByUser(Session.user().id());
    }

    public Optional<Review> cari(String id) throws SQLException {
        return id == null || id.isBlank() ? Optional.empty() : model.findById(id.trim());
    }

    public String validasi(String isi) {
        if (isi == null || isi.isBlank()) {
            return "Isi review tidak boleh kosong";
        }
        if (isi.trim().length() > MAX_REVIEW) {
            return "Isi review maksimal " + MAX_REVIEW + " karakter.";
        }
        return null;
    }

    public void kirim(String isi, boolean anonim) throws SQLException {
        model.insert(Session.user().id(), isi.trim(), anonim);
    }

    public void hapus(String id) throws SQLException {
        Review review = model.findById(id).orElse(null);
        if (review == null) return;
        model.delete(id);
        Session.catatAktivitasAdmin("Review dihapus", review.penulis(),
                "ID Review: " + review.id() + "\nNama pengirim: " + review.nama()
                + "\nIdentitas ditampilkan: " + review.penulis()
                + "\nIsi review: " + review.isi()
                + "\nJumlah upvote: " + review.upvote());
    }

    public boolean sudahUpvote(String idReview) throws SQLException {
        return model.sudahUpvote(idReview, Session.user().id());
    }

    public void upvote(String idReview) throws SQLException {
        model.upvote(idReview, Session.user().id());
    }

    public void unvote(String idReview) throws SQLException {
        model.unvote(idReview, Session.user().id());
    }
}
