package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.KuesionerController;
import com.mycompany.feedbackfiber.model.Kuesioner;
import com.mycompany.feedbackfiber.model.Pertanyaan;
import java.awt.Dialog;
import java.sql.SQLException;
import java.util.List;

/**
 * Form buat/ubah kuesioner. Desain dibuat di NetBeans GUI Builder (KuesionerFormDialog.form).
 */
public class KuesionerFormDialog extends javax.swing.JDialog {

    private final KuesionerController ctl = new KuesionerController();
    private final Kuesioner existing;
    private final boolean terkunci;
    private boolean saved;

    public KuesionerFormDialog(Dialog owner, Kuesioner existing, boolean terkunci) {
        super(owner, existing == null ? "Buat Kuesioner" : "Ubah Kuesioner", true);
        this.existing = existing;
        this.terkunci = terkunci;
        initComponents();

        if (existing != null) {
            txtJudul.setText(existing.judul());
            txtDeskripsi.setText(existing.deskripsi());
            StringBuilder sb = new StringBuilder();
            for (Pertanyaan p : existing.pertanyaan()) {
                sb.append(p.isi()).append('\n');
            }
            txtPertanyaan.setText(sb.toString());
        }
        if (terkunci) {
            txtPertanyaan.setEditable(false);
        }
        lblNote.setVisible(terkunci);

        UI.hand(btnBatal, btnSimpan);
        UI.ukuranHalaman(this, owner);
    }

    public boolean isSaved() {
        return saved;
    }

    private void simpan() {
        String judul = txtJudul.getText();
        String deskripsi = txtDeskripsi.getText();
        String jenis = Kuesioner.SKALA;
        List<String> pertanyaan = KuesionerController.parsePertanyaan(txtPertanyaan.getText());

        String err = ctl.validasi(judul, deskripsi, pertanyaan, terkunci);
        if (err != null) {
            UI.warn(this, err);
            return;
        }
        try {
            if (existing == null) {
                String id = ctl.buat(judul, deskripsi, jenis, pertanyaan);
                UI.info(this, "Kuesioner berhasil terbuat (ID: " + id + ")");
            } else {
                ctl.ubah(existing.id(), judul, deskripsi, jenis, terkunci ? null : pertanyaan);
                UI.info(this, "Kuesioner berhasil diubah");
            }
            saved = true;
            dispose();
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        txtJudul = new javax.swing.JTextField();
        lblJudul = new javax.swing.JLabel();
        scrollDeskripsi = new javax.swing.JScrollPane();
        txtDeskripsi = new javax.swing.JTextArea();
        lblDeskripsi = new javax.swing.JLabel();
        scrollPertanyaan = new javax.swing.JScrollPane();
        txtPertanyaan = new javax.swing.JTextArea();
        lblPertanyaan = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pnlRoot.setBackground(new java.awt.Color(231, 231, 231));

        txtJudul.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJudul.setBackground(new java.awt.Color(255, 255, 255));
        txtJudul.setForeground(new java.awt.Color(27, 0, 1));
        txtJudul.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)), javax.swing.BorderFactory.createEmptyBorder(9, 11, 9, 11)));

        lblJudul.setText("Judul"); // NOI18N
        lblJudul.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(27, 0, 1));


        txtDeskripsi.setRows(3);
        txtDeskripsi.setColumns(40);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDeskripsi.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8));
        scrollDeskripsi.setViewportView(txtDeskripsi);

        lblDeskripsi.setText("Deskripsi"); // NOI18N
        lblDeskripsi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDeskripsi.setForeground(new java.awt.Color(27, 0, 1));


        txtPertanyaan.setRows(10);
        txtPertanyaan.setColumns(40);
        txtPertanyaan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPertanyaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8));
        scrollPertanyaan.setViewportView(txtPertanyaan);

        lblPertanyaan.setText("<html>Pertanyaan<br>(satu per baris)</html>"); // NOI18N
        lblPertanyaan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPertanyaan.setForeground(new java.awt.Color(27, 0, 1));

        lblNote.setText("<html><i>Kuesioner ini sudah dijawab pelanggan: hanya judul dan deskripsi yang dapat diubah.</i></html>"); // NOI18N
        lblNote.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNote.setForeground(new java.awt.Color(27, 0, 1));

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

        btnSimpan.setText("Simpan"); // NOI18N
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setBackground(new java.awt.Color(227, 5, 18));
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setOpaque(true);
        btnSimpan.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(txtJudul, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblJudul))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(scrollDeskripsi, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDeskripsi))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(scrollPertanyaan, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblPertanyaan))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblNote, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGap(0, 760, Short.MAX_VALUE)
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblJudul))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(scrollDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(lblDeskripsi))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(scrollPertanyaan, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addGap(92, 92, 92))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(lblPertanyaan))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblNote, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGap(0, 590, Short.MAX_VALUE)
        );

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE))
            .addGap(0, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
            .addGap(0, 590, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPertanyaan;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollDeskripsi;
    private javax.swing.JScrollPane scrollPertanyaan;
    private javax.swing.JTextArea txtDeskripsi;
    private javax.swing.JTextField txtJudul;
    private javax.swing.JTextArea txtPertanyaan;
    // End of variables declaration//GEN-END:variables
}
