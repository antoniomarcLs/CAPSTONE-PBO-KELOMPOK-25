package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.ReviewController;
import com.mycompany.feedbackfiber.model.Review;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Dialog upvote review untuk pelanggan. Desain dibuat di NetBeans GUI Builder (UpvoteDialog.form).
 */
public class UpvoteDialog extends javax.swing.JDialog {

    private final ReviewController ctl = new ReviewController();
    private final DefaultTableModel model = UI.reviewModel();

    public UpvoteDialog(Frame owner) {
        super(owner, "Upvote Review", true);
        initComponents();
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UI.styleTable(tabel);
        tabel.getColumnModel().getColumn(0).setMaxWidth(60);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(110);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(420);
        tabel.getColumnModel().getColumn(3).setMaxWidth(60);

        UI.hand(btnUpvote, btnKembali);

        muat();
        UI.ukuranHalaman(this, owner);
    }

    private void muat() {
        try {
            UI.fillReviews(model, ctl.daftar());
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    private void beriUpvote() {
        String id = txtId.getText().trim();
        try {
            Optional<Review> r = ctl.cari(id);
            if (r.isEmpty()) {
                UI.error(this, "ID review tidak valid");
                return;
            }
            Review rv = r.get();
            String ringkas = "Nama: " + rv.penulis() + "\nReview: " + rv.isi() + "\nJumlah vote: " + rv.upvote()
                    + "\n\nApakah kamu yakin ingin memberikan upvote pada review ini?";
            if (!UI.confirm(this, ringkas, "Konfirmasi Upvote")) {
                return;
            }
            if (!ctl.sudahUpvote(rv.id())) {
                ctl.upvote(rv.id());
                UI.info(this, "Upvote berhasil");
            } else {
                UI.warn(this, "Anda sudah memberikan upvote pada review ini");
                if (UI.confirm(this, "Ingin membatalkan upvote (unvote)?", "Unvote")) {
                    ctl.unvote(rv.id());
                    UI.info(this, "Unvote berhasil");
                }
            }
            muat();
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        scrollTabel = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        pnlAksi = new javax.swing.JPanel();
        lblIdReview = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        btnUpvote = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pnlRoot.setBackground(new java.awt.Color(231, 231, 231));

        lblInfo.setText("Berikan upvote untuk review yang kamu sukai!"); // NOI18N
        lblInfo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfo.setForeground(new java.awt.Color(27, 0, 1));


        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Isi Review", "Upvote"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabel.setRowHeight(34);
        tabel.setShowVerticalLines(false);
        tabel.setFillsViewportHeight(true);
        tabel.setGridColor(new java.awt.Color(238, 238, 238));
        tabel.setSelectionBackground(new java.awt.Color(252, 226, 228));
        tabel.setSelectionForeground(new java.awt.Color(27, 0, 1));
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        tabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelKeyReleased(evt);
            }
        });
        scrollTabel.setViewportView(tabel);

        pnlAksi.setOpaque(false);

        lblIdReview.setText("ID review:"); // NOI18N
        lblIdReview.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdReview.setForeground(new java.awt.Color(27, 0, 1));

        txtId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtId.setBackground(new java.awt.Color(255, 255, 255));
        txtId.setForeground(new java.awt.Color(27, 0, 1));
        txtId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)), javax.swing.BorderFactory.createEmptyBorder(9, 11, 9, 11)));
        txtId.setPreferredSize(new java.awt.Dimension(250, 40));

        btnUpvote.setText("Beri Upvote"); // NOI18N
        btnUpvote.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUpvote.setForeground(new java.awt.Color(255, 255, 255));
        btnUpvote.setBackground(new java.awt.Color(227, 5, 18));
        btnUpvote.setFocusPainted(false);
        btnUpvote.setBorderPainted(false);
        btnUpvote.setOpaque(true);
        btnUpvote.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnUpvote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpvoteActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali ke Menu"); // NOI18N
        btnKembali.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnKembali.setForeground(new java.awt.Color(255, 255, 255));
        btnKembali.setBackground(new java.awt.Color(227, 5, 18));
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setOpaque(true);
        btnKembali.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });
        pnlAksi.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        pnlAksi.add(lblIdReview);
        pnlAksi.add(txtId);
        pnlAksi.add(btnUpvote);
        pnlAksi.add(btnKembali);
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblInfo))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(scrollTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pnlAksi, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGap(0, 900, Short.MAX_VALUE)
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblInfo))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(scrollTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addGap(76, 76, 76))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGap(0, 525, Short.MAX_VALUE)
        );

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE))
            .addGap(0, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE))
            .addGap(0, 525, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpvoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpvoteActionPerformed
        beriUpvote();
    }//GEN-LAST:event_btnUpvoteActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        int r = tabel.getSelectedRow();
        if (r >= 0) {
            txtId.setText(String.valueOf(model.getValueAt(r, 0)));
        }
    }//GEN-LAST:event_tabelMouseClicked

    private void tabelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelKeyReleased
        int r = tabel.getSelectedRow();
        if (r >= 0) {
            txtId.setText(String.valueOf(model.getValueAt(r, 0)));
        }
    }//GEN-LAST:event_tabelKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnUpvote;
    private javax.swing.JLabel lblIdReview;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JPanel pnlAksi;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollTabel;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
