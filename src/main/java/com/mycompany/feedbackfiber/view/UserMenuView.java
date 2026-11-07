package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.Session;
import com.mycompany.feedbackfiber.controller.KuesionerController;
import com.mycompany.feedbackfiber.controller.ReviewController;
import com.mycompany.feedbackfiber.model.HistoriKuesioner;
import com.mycompany.feedbackfiber.model.Review;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Menu utama pelanggan FIBER: tulis review, upvote, dan isi kuesioner.
 * Desain (header, halaman Dashboard dan Profil) dibuat di NetBeans GUI Builder
 * melalui file UserMenuView.form.
 */
public class UserMenuView extends javax.swing.JFrame {

    public UserMenuView() {
        initComponents();
        UI.ukuranHalaman(this);
        btnProfile.setIcon(UI.ikonOrang(30, UI.BURGUNDY));
        UI.hand(btnLogout, btnProfile, btnProfilKembali, btnReviewAksi, btnUpvoteAksi, btnKuesionerAksi);
        isiDataAkun();
        tampilkanHistoriFeedback();
        showPage("dashboard");
    }

    /** Mengisi data akun yang sedang login. Teks halaman lainnya sudah diatur di desain form. */
    private void isiDataAkun() {
        lblHalo.setText("Halo, " + Session.user().nama() + "!");
        lblIdValue.setText(nilai(Session.user().nama()));
        lblNamaValue.setText(Session.user().tanggalLahir() == null ? "-"
                : Session.user().tanggalLahir().format(
                        DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.of("id", "ID"))));
        lblRoleValue.setText(nilai(Session.user().gender()));
        lblDomisiliValue.setText(nilai(Session.user().domisili()));
    }

    /**
     * Menampilkan histori feedback milik akun yang sedang aktif.
     * Setiap query menggunakan ID dari Session, sehingga histori antar pengguna terpisah.
     */
    private void tampilkanHistoriFeedback() {
        pnlRecent.removeAll();
        pnlRecent.setLayout(new BorderLayout(0, 12));
        pnlRecent.setBorder(BorderFactory.createEmptyBorder(18, 20, 16, 20));

        JLabel judul = new JLabel("Histori Feedback Saya");
        judul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        judul.setForeground(new Color(27, 0, 1));
        JLabel keterangan = new JLabel("Riwayat review dan kuesioner yang pernah Anda kirim.");
        keterangan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        keterangan.setForeground(new Color(119, 119, 119));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(judul);
        header.add(Box.createVerticalStrut(5));
        header.add(keterangan);
        pnlRecent.add(header, BorderLayout.NORTH);

        JPanel daftar = new JPanel();
        daftar.setOpaque(false);
        daftar.setLayout(new BoxLayout(daftar, BoxLayout.Y_AXIS));

        try {
            String idUser = Session.user().id();
            List<Review> reviews = new ReviewController().historiSaya();
            List<HistoriKuesioner> kuesioner = new KuesionerController().historiSaya();

            tambahSubjudul(daftar, "Review yang Pernah Dikirim (" + reviews.size() + ")");
            if (reviews.isEmpty()) {
                tambahPesanKosong(daftar, "Anda belum pernah mengirim review.");
            } else {
                for (Review review : reviews) {
                    JPanel item = kartuHistori();
                    JLabel isi = new JLabel("<html><div style='width:850px'>"
                            + escapeHtml(review.isi()).replace("\n", "<br>")
                            + "</div></html>");
                    isi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    isi.setForeground(new Color(45, 45, 45));
                    JLabel meta = new JLabel((review.anonim() ? "Anonim" : "Review Anda")
                            + "  •  " + review.upvote() + " upvote");
                    meta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    meta.setForeground(new Color(119, 119, 119));
                    item.add(isi);
                    item.add(Box.createVerticalStrut(7));
                    item.add(meta);
                    daftar.add(item);
                    daftar.add(Box.createVerticalStrut(8));
                }
            }

            daftar.add(Box.createVerticalStrut(8));
            tambahSubjudul(daftar, "Kuesioner yang Pernah Diisi (" + kuesioner.size() + ")");
            if (kuesioner.isEmpty()) {
                tambahPesanKosong(daftar, "Anda belum pernah mengisi kuesioner.");
            } else {
                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                for (HistoriKuesioner h : kuesioner) {
                    JPanel item = kartuHistori();
                    JLabel nama = new JLabel(escapeHtml(h.judul()));
                    nama.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    nama.setForeground(new Color(45, 45, 45));
                    String tanggal = h.tanggal() == null ? "-" : h.tanggal().format(formatTanggal);
                    JLabel meta = new JLabel("Diisi: " + tanggal + "  •  "
                            + h.jumlahJawaban() + " jawaban  •  Rata-rata: "
                            + String.format(java.util.Locale.US, "%.2f / 5", h.rataRata()));
                    meta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    meta.setForeground(new Color(119, 119, 119));
                    item.add(nama);
                    item.add(Box.createVerticalStrut(7));
                    item.add(meta);
                    daftar.add(item);
                    daftar.add(Box.createVerticalStrut(8));
                }
            }
        } catch (Exception ex) {
            JLabel error = new JLabel("<html>Histori tidak dapat dimuat. Periksa koneksi database.<br>"
                    + escapeHtml(ex.getMessage()) + "</html>");
            error.setForeground(new Color(180, 0, 0));
            daftar.add(error);
        }

        JScrollPane scroll = new JScrollPane(daftar);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        pnlRecent.add(scroll, BorderLayout.CENTER);
        pnlRecent.revalidate();
        pnlRecent.repaint();
    }

    private JPanel kartuHistori() {
        JPanel item = new JPanel();
        item.setOpaque(true);
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(232, 232, 232)),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        return item;
    }

    private void tambahSubjudul(JPanel parent, String teks) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(124, 3, 8));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createVerticalStrut(9));
    }

    private void tambahPesanKosong(JPanel parent, String teks) {
        JLabel label = new JLabel(teks);
        label.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        label.setForeground(new Color(119, 119, 119));
        label.setBorder(BorderFactory.createEmptyBorder(4, 2, 12, 2));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
    }

    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private static String nilai(String v) {
        return v == null ? "-" : v;
    }

    private void showPage(String key) {
        ((CardLayout) pnlContent.getLayout()).show(pnlContent, key);
    }

    private void openReview() {
        new UpvoteDialog(this).setVisible(true);
        tampilkanHistoriFeedback();
    }

    private void openQuestionnaire() {
        new KuesionerUserDialog(this).setVisible(true);
        tampilkanHistoriFeedback();
    }

    private void openCreateReview() {
        new ReviewUserDialog(this).setVisible(true);
        tampilkanHistoriFeedback();
    }

    private void logout() {
        if (UI.confirm(this, "Akhiri sesi dan kembali ke halaman login?", "Logout")) {
            Session.clear();
            new LoginView().setVisible(true);
            dispose();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogout = new javax.swing.JButton();
        pnlTop = new javax.swing.JPanel();
        lblApp = new javax.swing.JLabel();
        btnProfile = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        pnlIntro = new javax.swing.JPanel();
        lblHalo = new javax.swing.JLabel();
        lblIntroDesc = new javax.swing.JLabel();
        pnlActions = new javax.swing.JPanel();
        pnlCardReview = new javax.swing.JPanel();
        lblReviewTitle = new javax.swing.JLabel();
        lblReviewDesc = new javax.swing.JLabel();
        btnReviewAksi = new javax.swing.JButton();
        pnlCardUpvote = new javax.swing.JPanel();
        lblUpvoteTitle = new javax.swing.JLabel();
        lblUpvoteDesc = new javax.swing.JLabel();
        btnUpvoteAksi = new javax.swing.JButton();
        pnlCardKuesioner = new javax.swing.JPanel();
        lblKuesionerTitle = new javax.swing.JLabel();
        lblKuesionerDesc = new javax.swing.JLabel();
        btnKuesionerAksi = new javax.swing.JButton();
        pnlRecent = new javax.swing.JPanel();
        lblRecentTitle = new javax.swing.JLabel();
        lblRecentText = new javax.swing.JLabel();
        pnlProfile = new javax.swing.JPanel();
        lblDomisiliLabel = new javax.swing.JLabel();
        lblDomisiliValue = new javax.swing.JLabel();
        sepDomisili = new javax.swing.JSeparator();
        pnlProfilCard = new javax.swing.JPanel();
        lblProfilTitle = new javax.swing.JLabel();
        lblProfilSub = new javax.swing.JLabel();
        lblIdLabel = new javax.swing.JLabel();
        lblIdValue = new javax.swing.JLabel();
        sepId = new javax.swing.JSeparator();
        lblNamaLabel = new javax.swing.JLabel();
        lblNamaValue = new javax.swing.JLabel();
        sepNama = new javax.swing.JSeparator();
        lblRoleLabel = new javax.swing.JLabel();
        lblRoleValue = new javax.swing.JLabel();
        sepRole = new javax.swing.JSeparator();
        btnProfilKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Feedback FIBER - Pelanggan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        btnLogout.setText("Logout"); // NOI18N
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setBackground(new java.awt.Color(227, 5, 18));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setOpaque(true);
        btnLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        pnlTop.setBackground(new java.awt.Color(255, 255, 255));

        lblApp.setText("Customer Feedback"); // NOI18N
        lblApp.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblApp.setForeground(new java.awt.Color(27, 0, 1));

        btnProfile.setToolTipText("Profil Saya"); // NOI18N
        btnProfile.setForeground(new java.awt.Color(124, 3, 8));
        btnProfile.setBackground(new java.awt.Color(255, 255, 255));
        btnProfile.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 12, 3, 12));
        btnProfile.setFocusPainted(false);
        btnProfile.setContentAreaFilled(false);
        btnProfile.setBorderPainted(false);
        btnProfile.setOpaque(false);
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblApp))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTopLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addGap(28, 28, 28))
            .addGap(0, 1148, Short.MAX_VALUE)
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblApp)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGap(0, 62, Short.MAX_VALUE)
        );

        pnlContent.setBackground(new java.awt.Color(231, 231, 231));

        pnlDashboard.setBackground(new java.awt.Color(231, 231, 231));

        pnlIntro.setBackground(new java.awt.Color(255, 255, 255));

        lblHalo.setText("Halo, Nama Pengguna!"); // NOI18N
        lblHalo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHalo.setForeground(new java.awt.Color(27, 0, 1));

        lblIntroDesc.setText("Terima kasih telah menjadi bagian dari FIBER. Bagikan pengalaman Anda untuk membantu kami meningkatkan kualitas layanan."); // NOI18N
        lblIntroDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblIntroDesc.setForeground(new java.awt.Color(119, 119, 119));
        javax.swing.GroupLayout pnlIntroLayout = new javax.swing.GroupLayout(pnlIntro);
        pnlIntro.setLayout(pnlIntroLayout);
        pnlIntroLayout.setHorizontalGroup(
            pnlIntroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIntroLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblHalo))
            .addGroup(pnlIntroLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblIntroDesc, 0, 1038, Short.MAX_VALUE)
                .addGap(25, 25, 25))
            .addGap(0, 1088, Short.MAX_VALUE)
        );
        pnlIntroLayout.setVerticalGroup(
            pnlIntroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIntroLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblHalo))
            .addGroup(pnlIntroLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(lblIntroDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 101, Short.MAX_VALUE)
        );

        pnlActions.setOpaque(false);

        pnlCardReview.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardReview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblReviewTitle.setText("Berikan Review"); // NOI18N
        lblReviewTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblReviewTitle.setForeground(new java.awt.Color(27, 0, 1));

        lblReviewDesc.setText("Bagikan pengalaman menggunakan layanan FIBER."); // NOI18N
        lblReviewDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblReviewDesc.setForeground(new java.awt.Color(119, 119, 119));

        btnReviewAksi.setText("Tulis Review"); // NOI18N
        btnReviewAksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReviewAksi.setForeground(new java.awt.Color(255, 255, 255));
        btnReviewAksi.setBackground(new java.awt.Color(227, 5, 18));
        btnReviewAksi.setFocusPainted(false);
        btnReviewAksi.setBorderPainted(false);
        btnReviewAksi.setOpaque(true);
        btnReviewAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnReviewAksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReviewAksiActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlCardReviewLayout = new javax.swing.GroupLayout(pnlCardReview);
        pnlCardReview.setLayout(pnlCardReviewLayout);
        pnlCardReviewLayout.setHorizontalGroup(
            pnlCardReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblReviewTitle))
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblReviewDesc, 0, 494, Short.MAX_VALUE)
                .addGap(16, 16, 16))
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnReviewAksi))
            .addGap(0, 536, Short.MAX_VALUE)
        );
        pnlCardReviewLayout.setVerticalGroup(
            pnlCardReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblReviewTitle))
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblReviewDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCardReviewLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(btnReviewAksi))
            .addGap(0, 141, Short.MAX_VALUE)
        );

        pnlCardUpvote.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardUpvote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblUpvoteTitle.setText("Berikan Upvote"); // NOI18N
        lblUpvoteTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblUpvoteTitle.setForeground(new java.awt.Color(27, 0, 1));

        lblUpvoteDesc.setText("Dukung review pelanggan lain."); // NOI18N
        lblUpvoteDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblUpvoteDesc.setForeground(new java.awt.Color(119, 119, 119));

        btnUpvoteAksi.setText("Beri Upvote"); // NOI18N
        btnUpvoteAksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUpvoteAksi.setForeground(new java.awt.Color(255, 255, 255));
        btnUpvoteAksi.setBackground(new java.awt.Color(227, 5, 18));
        btnUpvoteAksi.setFocusPainted(false);
        btnUpvoteAksi.setBorderPainted(false);
        btnUpvoteAksi.setOpaque(true);
        btnUpvoteAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnUpvoteAksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpvoteAksiActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlCardUpvoteLayout = new javax.swing.GroupLayout(pnlCardUpvote);
        pnlCardUpvote.setLayout(pnlCardUpvoteLayout);
        pnlCardUpvoteLayout.setHorizontalGroup(
            pnlCardUpvoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblUpvoteTitle))
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblUpvoteDesc, 0, 494, Short.MAX_VALUE)
                .addGap(16, 16, 16))
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUpvoteAksi))
            .addGap(0, 536, Short.MAX_VALUE)
        );
        pnlCardUpvoteLayout.setVerticalGroup(
            pnlCardUpvoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblUpvoteTitle))
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblUpvoteDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCardUpvoteLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(btnUpvoteAksi))
            .addGap(0, 141, Short.MAX_VALUE)
        );

        pnlCardKuesioner.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardKuesioner.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblKuesionerTitle.setText("Isi Kuesioner"); // NOI18N
        lblKuesionerTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblKuesionerTitle.setForeground(new java.awt.Color(27, 0, 1));

        lblKuesionerDesc.setText("Bantu kami mengevaluasi kualitas layanan."); // NOI18N
        lblKuesionerDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKuesionerDesc.setForeground(new java.awt.Color(119, 119, 119));

        btnKuesionerAksi.setText("Lihat Kuesioner"); // NOI18N
        btnKuesionerAksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnKuesionerAksi.setForeground(new java.awt.Color(255, 255, 255));
        btnKuesionerAksi.setBackground(new java.awt.Color(227, 5, 18));
        btnKuesionerAksi.setFocusPainted(false);
        btnKuesionerAksi.setBorderPainted(false);
        btnKuesionerAksi.setOpaque(true);
        btnKuesionerAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnKuesionerAksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKuesionerAksiActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlCardKuesionerLayout = new javax.swing.GroupLayout(pnlCardKuesioner);
        pnlCardKuesioner.setLayout(pnlCardKuesionerLayout);
        pnlCardKuesionerLayout.setHorizontalGroup(
            pnlCardKuesionerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKuesionerTitle))
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblKuesionerDesc, 0, 494, Short.MAX_VALUE)
                .addGap(16, 16, 16))
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnKuesionerAksi))
            .addGap(0, 536, Short.MAX_VALUE)
        );
        pnlCardKuesionerLayout.setVerticalGroup(
            pnlCardKuesionerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblKuesionerTitle))
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblKuesionerDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(btnKuesionerAksi))
            .addGap(0, 141, Short.MAX_VALUE)
        );
        pnlActions.setLayout(new java.awt.GridLayout(1, 3, 16, 0));
        pnlActions.add(pnlCardReview);
        pnlActions.add(pnlCardUpvote);
        pnlActions.add(pnlCardKuesioner);

        pnlRecent.setBackground(new java.awt.Color(255, 255, 255));
        pnlRecent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblRecentTitle.setText("Mulai Berikan Feedback"); // NOI18N
        lblRecentTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblRecentTitle.setForeground(new java.awt.Color(27, 0, 1));

        lblRecentText.setText("Review dan kuesioner membantu FIBER memahami pengalaman pelanggan serta menentukan aspek layanan yang perlu ditingkatkan."); // NOI18N
        lblRecentText.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblRecentText.setForeground(new java.awt.Color(119, 119, 119));
        javax.swing.GroupLayout pnlRecentLayout = new javax.swing.GroupLayout(pnlRecent);
        pnlRecent.setLayout(pnlRecentLayout);
        pnlRecentLayout.setHorizontalGroup(
            pnlRecentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblRecentTitle))
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblRecentText, 0, 1046, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGap(0, 1088, Short.MAX_VALUE)
        );
        pnlRecentLayout.setVerticalGroup(
            pnlRecentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblRecentTitle))
            .addGroup(pnlRecentLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(lblRecentText, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 378, Short.MAX_VALUE)
        );
        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlIntro, 0, 1088, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlActions, 0, 1088, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlRecent, 0, 1088, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGap(0, 1148, Short.MAX_VALUE)
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlIntro, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(pnlActions, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(pnlRecent, 0, 378, Short.MAX_VALUE)
                .addGap(26, 26, 26))
            .addGap(0, 706, Short.MAX_VALUE)
        );

        pnlProfile.setBackground(new java.awt.Color(231, 231, 231));

        pnlProfilCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfilCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblProfilTitle.setText("Profil Saya"); // NOI18N
        lblProfilTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblProfilTitle.setForeground(new java.awt.Color(27, 0, 1));

        lblProfilSub.setText("Informasi akun yang sedang digunakan."); // NOI18N
        lblProfilSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblProfilSub.setForeground(new java.awt.Color(119, 119, 119));

        lblIdLabel.setText("Nama Pengguna"); // NOI18N
        lblIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdLabel.setForeground(new java.awt.Color(119, 119, 119));

        lblIdValue.setText("Nama Lengkap"); // NOI18N
        lblIdValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdValue.setForeground(new java.awt.Color(27, 0, 1));


        lblNamaLabel.setText("Tanggal Lahir"); // NOI18N
        lblNamaLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNamaLabel.setForeground(new java.awt.Color(119, 119, 119));

        lblNamaValue.setText("01 Januari 2003"); // NOI18N
        lblNamaValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNamaValue.setForeground(new java.awt.Color(27, 0, 1));


        lblRoleLabel.setText("Gender"); // NOI18N
        lblRoleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRoleLabel.setForeground(new java.awt.Color(119, 119, 119));

        lblRoleValue.setText("Laki-Laki"); // NOI18N
        lblRoleValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRoleValue.setForeground(new java.awt.Color(27, 0, 1));


        lblDomisiliLabel.setText("Domisili"); // NOI18N
        lblDomisiliLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDomisiliLabel.setForeground(new java.awt.Color(119, 119, 119));

        lblDomisiliValue.setText("-"); // NOI18N
        lblDomisiliValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDomisiliValue.setForeground(new java.awt.Color(27, 0, 1));

        sepDomisili.setForeground(new java.awt.Color(70, 110, 255));

        btnProfilKembali.setText("←  Kembali ke Dashboard"); // NOI18N
        btnProfilKembali.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnProfilKembali.setForeground(new java.awt.Color(124, 3, 8));
        btnProfilKembali.setBackground(new java.awt.Color(255, 255, 255));
        btnProfilKembali.setFocusPainted(false);
        btnProfilKembali.setBorderPainted(false);
        btnProfilKembali.setOpaque(true);
        btnProfilKembali.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnProfilKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfilKembaliActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlProfilCardLayout = new javax.swing.GroupLayout(pnlProfilCard);
        pnlProfilCard.setLayout(pnlProfilCardLayout);
        pnlProfilCardLayout.setHorizontalGroup(
            pnlProfilCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblProfilTitle))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblProfilSub))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(lblIdValue, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sepId, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblNamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(lblNamaValue, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sepNama, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblRoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(lblRoleValue, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sepRole, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblDomisiliLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(lblDomisiliValue, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sepDomisili, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnProfilKembali)
                .addGap(12, 12, 12)
                .addComponent(btnLogout))
            .addGap(0, 1088, Short.MAX_VALUE)
        );
        pnlProfilCardLayout.setVerticalGroup(
            pnlProfilCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblProfilTitle))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblProfilSub))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(lblIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(lblIdValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(sepId, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(lblNamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(lblNamaValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(sepNama, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(lblRoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(lblRoleValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(sepRole, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(lblDomisiliLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(lblDomisiliValue, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(sepDomisili, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(btnProfilKembali))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(btnLogout))
            .addGap(0, 360, Short.MAX_VALUE)
        );
        javax.swing.GroupLayout pnlProfileLayout = new javax.swing.GroupLayout(pnlProfile);
        pnlProfile.setLayout(pnlProfileLayout);
        pnlProfileLayout.setHorizontalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlProfilCard, 0, 1088, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGap(0, 1148, Short.MAX_VALUE)
        );
        pnlProfileLayout.setVerticalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlProfilCard, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 706, Short.MAX_VALUE)
        );
        pnlContent.setLayout(new java.awt.CardLayout());
        pnlContent.add(pnlDashboard, "dashboard");
        pnlContent.add(pnlProfile, "profile");

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTop, 0, 1100, Short.MAX_VALUE)
            .addComponent(pnlContent, 0, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlContent, 0, 658, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        logout();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        showPage("profile");
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnProfilKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfilKembaliActionPerformed
        showPage("dashboard");
    }//GEN-LAST:event_btnProfilKembaliActionPerformed

    private void btnReviewAksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReviewAksiActionPerformed
        openCreateReview();
    }//GEN-LAST:event_btnReviewAksiActionPerformed

    private void btnUpvoteAksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpvoteAksiActionPerformed
        openReview();
    }//GEN-LAST:event_btnUpvoteAksiActionPerformed

    private void btnKuesionerAksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKuesionerAksiActionPerformed
        openQuestionnaire();
    }//GEN-LAST:event_btnKuesionerAksiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKuesionerAksi;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfilKembali;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReviewAksi;
    private javax.swing.JButton btnUpvoteAksi;
    private javax.swing.JLabel lblApp;
    private javax.swing.JLabel lblHalo;
    private javax.swing.JLabel lblDomisiliLabel;
    private javax.swing.JLabel lblDomisiliValue;
    private javax.swing.JLabel lblIdLabel;
    private javax.swing.JLabel lblIdValue;
    private javax.swing.JLabel lblIntroDesc;
    private javax.swing.JLabel lblKuesionerDesc;
    private javax.swing.JLabel lblKuesionerTitle;
    private javax.swing.JLabel lblNamaLabel;
    private javax.swing.JLabel lblNamaValue;
    private javax.swing.JLabel lblProfilSub;
    private javax.swing.JLabel lblProfilTitle;
    private javax.swing.JLabel lblRecentText;
    private javax.swing.JLabel lblRecentTitle;
    private javax.swing.JLabel lblReviewDesc;
    private javax.swing.JLabel lblReviewTitle;
    private javax.swing.JLabel lblRoleLabel;
    private javax.swing.JLabel lblRoleValue;
    private javax.swing.JLabel lblUpvoteDesc;
    private javax.swing.JLabel lblUpvoteTitle;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlCardKuesioner;
    private javax.swing.JPanel pnlCardReview;
    private javax.swing.JPanel pnlCardUpvote;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlIntro;
    private javax.swing.JPanel pnlProfilCard;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlRecent;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JSeparator sepDomisili;
    private javax.swing.JSeparator sepId;
    private javax.swing.JSeparator sepNama;
    private javax.swing.JSeparator sepRole;
    // End of variables declaration//GEN-END:variables
}
