package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.ReviewController;
import com.mycompany.feedbackfiber.model.Review;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Menu review untuk admin. Desain dialog dibuat di NetBeans GUI Builder (ReviewAdminDialog.form).
 */
public class ReviewAdminDialog extends javax.swing.JDialog {

    private final ReviewController ctl = new ReviewController();
    private final DefaultTableModel model = UI.reviewModel();

    public ReviewAdminDialog(Frame owner) {
        super(owner, "Menu Review", true);
        initComponents();
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UI.styleTable(tabel);
        tabel.getColumnModel().getColumn(0).setMaxWidth(60);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(110);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(420);
        tabel.getColumnModel().getColumn(3).setMaxWidth(60);

        UI.hand(btnHapus, btnKembali);

        try {
            UI.fillReviews(model, ctl.daftar());
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
        UI.ukuranHalaman(this, owner);
    }

    private void hapus() {
        int row = tabel.getSelectedRow();
        if (row < 0) {
            UI.info(this, "Pilih review pada tabel terlebih dahulu.");
            return;
        }
        String id = String.valueOf(model.getValueAt(row, 0));
        try {
            Optional<Review> r = ctl.cari(id);
            if (r.isEmpty()) {
                UI.error(this, "ID review tidak valid");
                return;
            }
            if (!UI.confirm(this, "Yakin ingin menghapus review " + id + "?\n"
                    + "Seluruh upvote pada review ini ikut terhapus.", "Hapus Review")) {
                return;
            }
            ctl.hapus(id);
            UI.info(this, "Review berhasil dihapus");
            dispose(); // PRD: setelah selesai kembali ke Menu Utama Admin
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        scrollTabel = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        pnlAksi = new javax.swing.JPanel();
        btnHapus = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pnlRoot.setBackground(new java.awt.Color(231, 231, 231));


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
        scrollTabel.setViewportView(tabel);

        pnlAksi.setOpaque(false);

        btnHapus.setText("Hapus Review"); // NOI18N
        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setBackground(new java.awt.Color(227, 5, 18));
        btnHapus.setFocusPainted(false);
        btnHapus.setBorderPainted(false);
        btnHapus.setOpaque(true);
        btnHapus.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKembali.setText("Kembali ke Menu Utama"); // NOI18N
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
        pnlAksi.add(btnHapus);
        pnlAksi.add(btnKembali);
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addComponent(scrollTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JPanel pnlAksi;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollTabel;
    private javax.swing.JTable tabel;
    // End of variables declaration//GEN-END:variables
}
