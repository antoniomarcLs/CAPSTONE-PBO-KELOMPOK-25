package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.KuesionerController;
import com.mycompany.feedbackfiber.model.Kuesioner;
import com.mycompany.feedbackfiber.model.Pertanyaan;
import java.awt.Dimension;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Menu kuesioner untuk admin. Desain dialog dibuat di NetBeans GUI Builder (KuesionerAdminDialog.form).
 */
public class KuesionerAdminDialog extends javax.swing.JDialog {

    private final KuesionerController ctl = new KuesionerController();
    private final DefaultTableModel model = UI.readOnly("ID", "Judul", "Deskripsi", "Jml Pertanyaan");

    public KuesionerAdminDialog(Frame owner) {
        super(owner, "Menu Kuesioner", true);
        initComponents();
        tabel.setModel(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UI.styleTable(tabel);
        tabel.getColumnModel().getColumn(0).setMaxWidth(60);
        tabel.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(380);
        tabel.getColumnModel().getColumn(3).setPreferredWidth(110);

        UI.hand(btnBuat, btnDetail, btnUbah, btnHapus, btnKembali);

        muat();
        UI.ukuranHalaman(this, owner);
    }

    private void muat() {
        model.setRowCount(0);
        try {
            for (Kuesioner k : ctl.daftar()) {
                model.addRow(new Object[]{k.id(), k.judul(), k.deskripsi(), k.jumlahPertanyaan()});
            }
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    private String idTerpilih() {
        int row = tabel.getSelectedRow();
        if (row < 0) {
            UI.info(this, "Pilih kuesioner pada tabel terlebih dahulu.");
            return null;
        }
        return String.valueOf(model.getValueAt(row, 0));
    }

    private void buat() {
        KuesionerFormDialog f = new KuesionerFormDialog(this, null, false);
        f.setVisible(true);
        if (f.isSaved()) {
            dispose();
        }
    }

    private void detail() {
        String id = idTerpilih();
        if (id == null) {
            return;
        }
        try {
            Optional<Kuesioner> k = ctl.cari(id);
            if (k.isEmpty()) {
                UI.error(this, "ID kuesioner tidak valid");
                return;
            }
            Kuesioner kq = k.get();
            StringBuilder sb = new StringBuilder();
            sb.append("ID        : ").append(kq.id()).append('\n');
            sb.append("Judul     : ").append(kq.judul()).append('\n');
            sb.append("Deskripsi : ").append(kq.deskripsi()).append('\n');
            int no = 1;
            for (Pertanyaan p : kq.pertanyaan()) {
                sb.append(no++).append(". ").append(p.isi()).append('\n');
            }
            JTextArea ta = new JTextArea(sb.toString(), 16, 60);
            ta.setEditable(false);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(560, 320));
            JOptionPane.showMessageDialog(this, sp, "Detail Kuesioner", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    private void ubah() {
        String id = idTerpilih();
        if (id == null) {
            return;
        }
        try {
            Optional<Kuesioner> k = ctl.cari(id);
            if (k.isEmpty()) {
                UI.error(this, "ID kuesioner tidak valid");
                return;
            }
            KuesionerFormDialog f = new KuesionerFormDialog(this, k.get(), ctl.punyaJawaban(id));
            f.setVisible(true);
            if (f.isSaved()) {
                dispose();
            }
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    private void hapus() {
        String id = idTerpilih();
        if (id == null) {
            return;
        }
        try {
            Optional<Kuesioner> k = ctl.cari(id);
            if (k.isEmpty()) {
                UI.error(this, "ID kuesioner tidak valid");
                return;
            }
            String peringatan = ctl.punyaJawaban(id)
                    ? "\n\nPERHATIAN: kuesioner ini sudah dijawab pelanggan. Seluruh jawaban ikut terhapus permanen."
                    : "";
            if (!UI.confirm(this, "Yakin ingin hapus kuesioner \"" + k.get().judul() + "\"?" + peringatan,
                    "Hapus Kuesioner")) {
                return;
            }
            ctl.hapus(id);
            UI.info(this, "Kuesioner berhasil dihapus");
            dispose();
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
        btnBuat = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
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
                "ID", "Judul", "Deskripsi", "Jml Pertanyaan"
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

        btnBuat.setText("Buat Kuesioner"); // NOI18N
        btnBuat.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnBuat.setForeground(new java.awt.Color(255, 255, 255));
        btnBuat.setBackground(new java.awt.Color(227, 5, 18));
        btnBuat.setFocusPainted(false);
        btnBuat.setBorderPainted(false);
        btnBuat.setOpaque(true);
        btnBuat.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnBuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatActionPerformed(evt);
            }
        });

        btnDetail.setText("Lihat Detail"); // NOI18N
        btnDetail.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDetail.setForeground(new java.awt.Color(255, 255, 255));
        btnDetail.setBackground(new java.awt.Color(227, 5, 18));
        btnDetail.setFocusPainted(false);
        btnDetail.setBorderPainted(false);
        btnDetail.setOpaque(true);
        btnDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah"); // NOI18N
        btnUbah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));
        btnUbah.setBackground(new java.awt.Color(227, 5, 18));
        btnUbah.setFocusPainted(false);
        btnUbah.setBorderPainted(false);
        btnUbah.setOpaque(true);
        btnUbah.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus"); // NOI18N
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
        pnlAksi.add(btnBuat);
        pnlAksi.add(btnDetail);
        pnlAksi.add(btnUbah);
        pnlAksi.add(btnHapus);
        pnlAksi.add(btnKembali);
        javax.swing.GroupLayout pnlRootLayout = new javax.swing.GroupLayout(pnlRoot);
        pnlRoot.setLayout(pnlRootLayout);
        pnlRootLayout.setHorizontalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(scrollTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pnlAksi, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGap(0, 1050, Short.MAX_VALUE)
        );
        pnlRootLayout.setVerticalGroup(
            pnlRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRootLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(scrollTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                .addGap(71, 71, 71))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRootLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlAksi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGap(0, 545, Short.MAX_VALUE)
        );

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE))
            .addGap(0, 1050, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
            .addGap(0, 545, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatActionPerformed
        buat();
    }//GEN-LAST:event_btnBuatActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        detail();
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        ubah();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuat;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnUbah;
    private javax.swing.JPanel pnlAksi;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JScrollPane scrollTabel;
    private javax.swing.JTable tabel;
    // End of variables declaration//GEN-END:variables
}
