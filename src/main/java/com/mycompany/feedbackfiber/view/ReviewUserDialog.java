package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.ReviewController;
import java.awt.Frame;
import java.sql.SQLException;

/**
 * Dialog isi review untuk pelanggan. Desain dibuat di NetBeans GUI Builder (ReviewUserDialog.form).
 */
public class ReviewUserDialog extends javax.swing.JDialog {

    private final ReviewController ctl = new ReviewController();

    public ReviewUserDialog(Frame owner) {
        super(owner, "Isi Review", true);
        initComponents();
        UI.hand(btnBatal, btnKirim, chkAnonim);
        UI.ukuranHalaman(this, owner);
    }

    private void kirim() {
        String isi = txtReview.getText();
        String err = ctl.validasi(isi);
        if (err != null) {
            UI.error(this, err);
            return;
        }
        if (!UI.confirm(this, "Apakah Anda yakin ingin mengirim review ini?", "Kirim Review")) {
            return;
        }
        try {
            ctl.kirim(isi, chkAnonim.isSelected());
            UI.info(this, "Review berhasil disimpan");
            dispose();
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        lblTulis = new javax.swing.JLabel();
        scrollReview = new javax.swing.JScrollPane();
        txtReview = new javax.swing.JTextArea();
        chkAnonim = new javax.swing.JCheckBox();
        pnlAksi = new javax.swing.JPanel();
        btnBatal = new javax.swing.JButton();
        btnKirim = new javax.swing.JButton();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pnlRoot.setBackground(new java.awt.Color(231, 231, 231));

        lblTulis.setText("Tulis review Anda:"); // NOI18N
        lblTulis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTulis.setForeground(new java.awt.Color(27, 0, 1));


        txtReview.setRows(8);
        txtReview.setColumns(40);
        txtReview.setLineWrap(true);
        txtReview.setWrapStyleWord(true);
        txtReview.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtReview.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        scrollReview.setViewportView(txtReview);

        chkAnonim.setText("Kirim sebagai anonim"); // NOI18N
        chkAnonim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkAnonim.setForeground(new java.awt.Color(27, 0, 1));
        chkAnonim.setOpaque(false);

        pnlAksi.setOpaque(false);

        btnBatal.setText("Batal"); // NOI18N
        btnBatal.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setBackground(new java.awt.Color(227, 5, 18));
        btnBatal.setFocusPainted(false);
        btnBatal.setBorderPainted(false);
        btnBatal.setOpaque(true);
        btnBatal.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnKirim.setText("Kirim Review"); // NOI18N
        btnKirim.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnKirim.setForeground(new java.awt.Color(255, 255, 255));
        btnKirim.setBackground(new java.awt.Color(227, 5, 18));
        btnKirim.setFocusPainted(false);
        btnKirim.setBorderPainted(false);
        btnKirim.setOpaque(true);
        btnKirim.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });
        pnlAksi.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        pnlAksi.add(btnBatal);
        pnlAksi.add(btnKirim);
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTulis))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(scrollReview, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(chkAnonim, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGap(0, 650, Short.MAX_VALUE)
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblTulis))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(scrollReview, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(chkAnonim, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGap(0, 435, Short.MAX_VALUE)
        );

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
            .addGap(0, 650, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
            .addGap(0, 435, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        kirim();
    }//GEN-LAST:event_btnKirimActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnKirim;
    private javax.swing.JCheckBox chkAnonim;
    private javax.swing.JLabel lblTulis;
    private javax.swing.JPanel pnlAksi;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollReview;
    private javax.swing.JTextArea txtReview;
    // End of variables declaration//GEN-END:variables
}
