package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.Session;
import java.awt.CardLayout;
import java.time.format.DateTimeFormatter;

/**
 * Menu utama admin FIBER: kelola review pelanggan dan kuesioner.
 * Desain (header, halaman Dashboard dan Profil) dibuat di NetBeans GUI Builder
 * melalui file AdminMenuView.form.
 */
public class AdminMenuView extends javax.swing.JFrame {

    public AdminMenuView() {
        initComponents();
        UI.ukuranHalaman(this);
        btnProfile.setIcon(UI.ikonOrang(30, UI.BURGUNDY));
        UI.hand(btnLogout, btnProfile, btnProfilKembali, btnReviewAksi, btnKuesionerAksi);
        isiDataAkun();
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
        lblProfilNote.setText("Domisili: " + nilai(Session.user().domisili()));
    }

    private static String nilai(String v) {
        return v == null ? "-" : v;
    }

    private void showPage(String key) {
        ((CardLayout) pnlContent.getLayout()).show(pnlContent, key);
    }

    private void openReview() {
        new ReviewAdminDialog(this).setVisible(true);
    }

    private void openQuestionnaire() {
        new KuesionerAdminDialog(this).setVisible(true);
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
        pnlCardKuesioner = new javax.swing.JPanel();
        lblKuesionerTitle = new javax.swing.JLabel();
        lblKuesionerDesc = new javax.swing.JLabel();
        btnKuesionerAksi = new javax.swing.JButton();
        pnlProfile = new javax.swing.JPanel();
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
        lblProfilNote = new javax.swing.JLabel();
        btnProfilKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Feedback FIBER - Admin");

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

        lblApp.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblApp.setForeground(new java.awt.Color(27, 0, 1));
        lblApp.setText("Feedback Management");

        btnProfile.setForeground(new java.awt.Color(124, 3, 8));
        btnProfile.setToolTipText("Profil Saya");
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
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTopLayout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblApp)
                    .addComponent(btnProfile))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pnlContent.setBackground(new java.awt.Color(231, 231, 231));
        pnlContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(231, 231, 231));

        pnlIntro.setBackground(new java.awt.Color(255, 255, 255));

        lblHalo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHalo.setForeground(new java.awt.Color(27, 0, 1));
        lblHalo.setText("Halo, Nama Pengguna!");

        lblIntroDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblIntroDesc.setForeground(new java.awt.Color(119, 119, 119));
        lblIntroDesc.setText("Kelola review pelanggan dan kuesioner layanan FIBER.");

        javax.swing.GroupLayout pnlIntroLayout = new javax.swing.GroupLayout(pnlIntro);
        pnlIntro.setLayout(pnlIntroLayout);
        pnlIntroLayout.setHorizontalGroup(
            pnlIntroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIntroLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlIntroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHalo)
                    .addGroup(pnlIntroLayout.createSequentialGroup()
                        .addComponent(lblIntroDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 1133, Short.MAX_VALUE)
                        .addGap(25, 25, 25))))
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
        pnlActions.setLayout(new java.awt.GridLayout(1, 2, 16, 0));

        pnlCardReview.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardReview.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblReviewTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblReviewTitle.setForeground(new java.awt.Color(27, 0, 1));
        lblReviewTitle.setText("Manajemen Review");

        lblReviewDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblReviewDesc.setForeground(new java.awt.Color(119, 119, 119));
        lblReviewDesc.setText("Lihat dan hapus review pelanggan.");

        btnReviewAksi.setBackground(new java.awt.Color(227, 5, 18));
        btnReviewAksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReviewAksi.setForeground(new java.awt.Color(255, 255, 255));
        btnReviewAksi.setText("Buka Review");
        btnReviewAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnReviewAksi.setBorderPainted(false);
        btnReviewAksi.setFocusPainted(false);
        btnReviewAksi.setOpaque(true);
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
                .addGap(21, 21, 21)
                .addGroup(pnlCardReviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblReviewTitle)
                    .addGroup(pnlCardReviewLayout.createSequentialGroup()
                        .addComponent(lblReviewDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addComponent(btnReviewAksi)))
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
        );

        pnlActions.add(pnlCardReview);

        pnlCardKuesioner.setBackground(new java.awt.Color(255, 255, 255));
        pnlCardKuesioner.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblKuesionerTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblKuesionerTitle.setForeground(new java.awt.Color(27, 0, 1));
        lblKuesionerTitle.setText("Manajemen Kuesioner");

        lblKuesionerDesc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblKuesionerDesc.setForeground(new java.awt.Color(119, 119, 119));
        lblKuesionerDesc.setText("Buat, lihat, ubah, dan hapus kuesioner.");

        btnKuesionerAksi.setBackground(new java.awt.Color(227, 5, 18));
        btnKuesionerAksi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnKuesionerAksi.setForeground(new java.awt.Color(255, 255, 255));
        btnKuesionerAksi.setText("Kelola Kuesioner");
        btnKuesionerAksi.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnKuesionerAksi.setBorderPainted(false);
        btnKuesionerAksi.setFocusPainted(false);
        btnKuesionerAksi.setOpaque(true);
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
                .addGap(21, 21, 21)
                .addGroup(pnlCardKuesionerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKuesionerTitle)
                    .addGroup(pnlCardKuesionerLayout.createSequentialGroup()
                        .addComponent(lblKuesionerDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addComponent(btnKuesionerAksi)))
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
        );

        pnlActions.add(pnlCardKuesioner);

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlIntro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlActions, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlIntro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(pnlActions, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContent.add(pnlDashboard, "dashboard");

        pnlProfile.setBackground(new java.awt.Color(231, 231, 231));

        pnlProfilCard.setBackground(new java.awt.Color(255, 255, 255));
        pnlProfilCard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 229, 229)));

        lblProfilTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblProfilTitle.setForeground(new java.awt.Color(27, 0, 1));
        lblProfilTitle.setText("Profil Saya");

        lblProfilSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblProfilSub.setForeground(new java.awt.Color(119, 119, 119));
        lblProfilSub.setText("Informasi akun yang sedang digunakan.");

        lblIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdLabel.setForeground(new java.awt.Color(119, 119, 119));
        lblIdLabel.setText("Nama Pengguna");

        lblIdValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdValue.setForeground(new java.awt.Color(27, 0, 1));
        lblIdValue.setText("Nama Lengkap");

        lblNamaLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNamaLabel.setForeground(new java.awt.Color(119, 119, 119));
        lblNamaLabel.setText("Tanggal Lahir");

        lblNamaValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNamaValue.setForeground(new java.awt.Color(27, 0, 1));
        lblNamaValue.setText("01 Januari 2003");

        lblRoleLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRoleLabel.setForeground(new java.awt.Color(119, 119, 119));
        lblRoleLabel.setText("Gender");

        lblRoleValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRoleValue.setForeground(new java.awt.Color(27, 0, 1));
        lblRoleValue.setText("Laki-Laki");

        lblProfilNote.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblProfilNote.setForeground(new java.awt.Color(119, 119, 119));
        lblProfilNote.setText("Domisili: -");

        btnProfilKembali.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnProfilKembali.setForeground(new java.awt.Color(124, 3, 8));
        btnProfilKembali.setText("←  Kembali ke Dashboard");
        btnProfilKembali.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnProfilKembali.setBorderPainted(false);
        btnProfilKembali.setFocusPainted(false);
        btnProfilKembali.setOpaque(true);
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
                .addGroup(pnlProfilCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblIdValue, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(sepId, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblNamaValue, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(sepNama, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(lblRoleValue, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(sepRole, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE))
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblProfilNote, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlProfilCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProfilTitle)
                    .addComponent(lblProfilSub)
                    .addComponent(lblIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRoleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlProfilCardLayout.createSequentialGroup()
                        .addComponent(btnProfilKembali)
                        .addGap(12, 12, 12)
                        .addComponent(btnLogout))))
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
                .addGap(249, 249, 249)
                .addComponent(lblProfilNote, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(btnProfilKembali))
            .addGroup(pnlProfilCardLayout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addComponent(btnLogout))
        );

        javax.swing.GroupLayout pnlProfileLayout = new javax.swing.GroupLayout(pnlProfile);
        pnlProfile.setLayout(pnlProfileLayout);
        pnlProfileLayout.setHorizontalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlProfilCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        pnlProfileLayout.setVerticalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(pnlProfilCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContent.add(pnlProfile, "profile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTop, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
            .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTop, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlContent, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
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
        openReview();
    }//GEN-LAST:event_btnReviewAksiActionPerformed

    private void btnKuesionerAksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKuesionerAksiActionPerformed
        openQuestionnaire();
    }//GEN-LAST:event_btnKuesionerAksiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKuesionerAksi;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfilKembali;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReviewAksi;
    private javax.swing.JLabel lblApp;
    private javax.swing.JLabel lblHalo;
    private javax.swing.JLabel lblIdLabel;
    private javax.swing.JLabel lblIdValue;
    private javax.swing.JLabel lblIntroDesc;
    private javax.swing.JLabel lblKuesionerDesc;
    private javax.swing.JLabel lblKuesionerTitle;
    private javax.swing.JLabel lblNamaLabel;
    private javax.swing.JLabel lblNamaValue;
    private javax.swing.JLabel lblProfilNote;
    private javax.swing.JLabel lblProfilSub;
    private javax.swing.JLabel lblProfilTitle;
    private javax.swing.JLabel lblReviewDesc;
    private javax.swing.JLabel lblReviewTitle;
    private javax.swing.JLabel lblRoleLabel;
    private javax.swing.JLabel lblRoleValue;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlCardKuesioner;
    private javax.swing.JPanel pnlCardReview;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlIntro;
    private javax.swing.JPanel pnlProfilCard;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JSeparator sepId;
    private javax.swing.JSeparator sepNama;
    private javax.swing.JSeparator sepRole;
    // End of variables declaration//GEN-END:variables
}
