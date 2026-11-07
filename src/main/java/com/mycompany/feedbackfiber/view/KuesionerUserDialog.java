package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.KuesionerController;
import com.mycompany.feedbackfiber.model.Kuesioner;
import com.mycompany.feedbackfiber.model.Pertanyaan;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dialog isi kuesioner untuk pelanggan. Desain dibuat di NetBeans GUI Builder (KuesionerUserDialog.form).
 * Baris pertanyaan dibuat dinamis dari database, jadi isi panelSoal di form hanyalah contoh tampilan.
 */
public class KuesionerUserDialog extends javax.swing.JDialog {

    private static final String PLACEHOLDER = "-- Pilih jawaban --";

    private final KuesionerController ctl = new KuesionerController();
    private final List<JComboBox<String>> isian = new ArrayList<>();
    private Kuesioner aktif;

    public KuesionerUserDialog(Frame owner) {
        super(owner, "Kuesioner", true);
        initComponents();

        // Hapus contoh baris dari desain; baris asli dibuat di tampilkan().
        panelSoal.removeAll();
        panelSoal.setLayout(new BoxLayout(panelSoal, BoxLayout.Y_AXIS));
        lblDeskripsi.setText(" ");
        btnSimpan.setEnabled(false);

        UI.hand(btnKembali, btnSimpan);
        

        try {
            for (Kuesioner k : ctl.daftar()) {
                cmbKuesioner.addItem(k);
            }
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
        // tampilkan() dipanggil otomatis oleh cmbKuesioner di Builder saat item pertama ditambahkan.
        if (cmbKuesioner.getItemCount() == 0) {
            lblDeskripsi.setText("Belum ada kuesioner.");
        }
        UI.ukuranHalaman(this, owner);
    }

    private void tampilkan() {
        panelSoal.removeAll();
        isian.clear();
        aktif = null;
        btnSimpan.setEnabled(true);
        Kuesioner pilih = (Kuesioner) cmbKuesioner.getSelectedItem();
        if (pilih == null) {
            return;
        }
        try {
            if (ctl.sudahDiisi(pilih.id())) {
                lblDeskripsi.setText("Anda sudah mengisi kuesioner ini.");
                refresh();
                return;
            }
            aktif = ctl.cari(pilih.id()).orElse(null);
        } catch (SQLException e) {
            UI.dbError(this, e);
            return;
        }
        if (aktif == null) {
            lblDeskripsi.setText("Kuesioner tidak ditemukan.");
            refresh();
            return;
        }
        lblDeskripsi.setText("<html>" + UI.html(aktif.deskripsi()) + "</html>");
        String[] opsi = Kuesioner.opsi(aktif.jenisJawaban());
        int no = 1;
        for (Pertanyaan p : aktif.pertanyaan()) {
            JLabel l = new JLabel("<html><body style='width:520px'>" + no++ + ". " + UI.html(p.isi()) + "</body></html>");
            JComboBox<String> c = new JComboBox<>();
            c.addItem(PLACEHOLDER);
            for (String o : opsi) {
                c.addItem(o);
            }
            JPanel row = new JPanel(new BorderLayout(4, 4));
            row.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
            row.add(l, BorderLayout.NORTH);
            row.add(c, BorderLayout.CENTER);
            panelSoal.add(row);
            isian.add(c);
        }
        btnSimpan.setEnabled(!aktif.pertanyaan().isEmpty());
        refresh();
    }

    private void refresh() {
        panelSoal.revalidate();
        panelSoal.repaint();
    }

    private void simpan() {
        if (aktif == null) {
            return;
        }
        for (JComboBox<String> c : isian) {
            if (c.getSelectedIndex() <= 0) {
                UI.warn(this, "Masih ada pertanyaan yang belum dijawab.\n"
                        + "Jawaban yang sudah Anda isi tetap tersimpan di formulir; silakan lanjutkan mengisi.");
                return;
            }
        }
        String[] poin = Kuesioner.poin(aktif.jenisJawaban());
        Map<String, String> jawaban = new LinkedHashMap<>();
        for (int i = 0; i < isian.size(); i++) {
            jawaban.put(aktif.pertanyaan().get(i).id(), poin[isian.get(i).getSelectedIndex() - 1]);
        }
        try {
            ctl.simpanJawaban(jawaban);
            UI.info(this, "Jawaban berhasil disimpan");
            dispose();
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        lblPilih = new javax.swing.JLabel();
        cmbKuesioner = new javax.swing.JComboBox<>();
        lblDeskripsi = new javax.swing.JLabel();
        scrollSoal = new javax.swing.JScrollPane();
        panelSoal = new javax.swing.JPanel();
        lblContohSoal1 = new javax.swing.JLabel();
        cmbContohJawaban1 = new javax.swing.JComboBox<>();
        lblContohSoal2 = new javax.swing.JLabel();
        cmbContohJawaban2 = new javax.swing.JComboBox<>();
        pnlAksi = new javax.swing.JPanel();
        btnKembali = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        pnlRoot.setBackground(new java.awt.Color(231, 231, 231));

        lblPilih.setText("Pilih kuesioner:"); // NOI18N
        lblPilih.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPilih.setForeground(new java.awt.Color(27, 0, 1));

        cmbKuesioner.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbKuesioner.setBackground(new java.awt.Color(255, 255, 255));
        cmbKuesioner.setForeground(new java.awt.Color(27, 0, 1));
        cmbKuesioner.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)), javax.swing.BorderFactory.createEmptyBorder(9, 11, 9, 11)));
        cmbKuesioner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKuesionerActionPerformed(evt);
            }
        });

        lblDeskripsi.setText("Deskripsi kuesioner yang dipilih tampil di sini."); // NOI18N
        lblDeskripsi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDeskripsi.setForeground(new java.awt.Color(27, 0, 1));


        panelSoal.setBackground(new java.awt.Color(255, 255, 255));

        lblContohSoal1.setText("1. Contoh pertanyaan kuesioner"); // NOI18N
        lblContohSoal1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblContohSoal1.setForeground(new java.awt.Color(27, 0, 1));

        cmbContohJawaban1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih jawaban --", "1 - Sangat Tidak Puas", "2 - Tidak Puas", "3 - Cukup", "4 - Puas", "5 - Sangat Puas" }));

        lblContohSoal2.setText("2. Contoh pertanyaan kuesioner berikutnya"); // NOI18N
        lblContohSoal2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblContohSoal2.setForeground(new java.awt.Color(27, 0, 1));

        cmbContohJawaban2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih jawaban --", "1 - Sangat Tidak Puas", "2 - Tidak Puas", "3 - Cukup", "4 - Puas", "5 - Sangat Puas" }));
        javax.swing.GroupLayout panelSoalLayout = new javax.swing.GroupLayout(panelSoal);
        panelSoal.setLayout(panelSoalLayout);
        panelSoalLayout.setHorizontalGroup(
            panelSoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblContohSoal1))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(cmbContohJawaban1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                .addGap(8, 8, 8))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblContohSoal2))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(cmbContohJawaban2, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                .addGap(8, 8, 8))
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelSoalLayout.setVerticalGroup(
            panelSoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblContohSoal1))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(cmbContohJawaban1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(lblContohSoal2))
            .addGroup(panelSoalLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(cmbContohJawaban2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 150, Short.MAX_VALUE)
        );
        scrollSoal.setViewportView(panelSoal);

        pnlAksi.setOpaque(false);

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

        btnSimpan.setText("Simpan Jawaban"); // NOI18N
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
        pnlAksi.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        pnlAksi.add(btnKembali);
        pnlAksi.add(btnSimpan);
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblPilih))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(cmbKuesioner, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblDeskripsi, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(scrollSoal, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pnlAksi, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGap(0, 850, Short.MAX_VALUE)
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblPilih))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(cmbKuesioner, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(lblDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(scrollSoal, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGap(0, 610, Short.MAX_VALUE)
        );

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE))
            .addGap(0, 850, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
            .addGap(0, 610, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbKuesionerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKuesionerActionPerformed
        tampilkan();
    }//GEN-LAST:event_cmbKuesionerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbContohJawaban1;
    private javax.swing.JComboBox<String> cmbContohJawaban2;
    private javax.swing.JComboBox<com.mycompany.feedbackfiber.model.Kuesioner> cmbKuesioner;
    private javax.swing.JLabel lblContohSoal1;
    private javax.swing.JLabel lblContohSoal2;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblPilih;
    private javax.swing.JPanel panelSoal;
    private javax.swing.JPanel pnlAksi;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollSoal;
    // End of variables declaration//GEN-END:variables
}
