package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.model.Review;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Pusat tema tampilan FIBER: warna, font, gaya tabel, dan dialog pesan yang dipakai
 * bersama oleh seluruh form. Kelas ini juga berupa JPanel Form (UI.form) yang menampilkan
 * pratinjau tema tersebut di NetBeans GUI Builder. Seluruh method static tetap dipakai form lain.
 */
public class UI extends javax.swing.JPanel {
    public static final Color RED = Color.decode("#E30512");
    public static final Color BURGUNDY = Color.decode("#7C0308");
    public static final Color TEXT = Color.decode("#1B0001");
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 14);

    /** Ukuran semua halaman, mengikuti LoginView. */
    public static final Dimension UKURAN_HALAMAN = new Dimension(1100, 720);

    public UI() {
        initComponents();
        hand(btnUtama, btnSekunder);
    }

    /** Ukuran seragam, tidak bisa di-maximize/di-resize, dan diletakkan di tengah layar. */
    public static void ukuranHalaman(JFrame f) {
        f.setResizable(false);
        f.setSize(UKURAN_HALAMAN);
        f.setLocationRelativeTo(null);
    }

    public static void ukuranHalaman(JDialog d, Component owner) {
        d.setResizable(false);
        d.setSize(UKURAN_HALAMAN);
        d.setLocationRelativeTo(owner);
    }

    /** Ikon orang default (kepala + bahu) yang digambar langsung, tidak bergantung pada font. */
    public static Icon ikonOrang(int ukuran, Color warna) {
        return new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(warna);
                int kepala = ukuran * 42 / 100;
                g2.fillOval(x + (ukuran - kepala) / 2, y + ukuran * 6 / 100, kepala, kepala);
                int bahuTinggi = ukuran * 44 / 100;
                int bahuLebar = ukuran * 80 / 100;
                g2.setClip(x, y, ukuran, ukuran);
                g2.fillOval(x + (ukuran - bahuLebar) / 2, y + ukuran * 56 / 100, bahuLebar, bahuTinggi * 2);
                g2.dispose();
            }
            @Override public int getIconWidth() { return ukuran; }
            @Override public int getIconHeight() { return ukuran; }
        };
    }

    /** Kursor tangan untuk tombol; dipakai setelah initComponents() karena kursor tidak diatur di form. */
    public static void hand(AbstractButton... buttons) {
        for (AbstractButton b : buttons) {
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    public static void styleTable(JTable table) {
        JTableHeader h = table.getTableHeader();

    h.setFont(new Font("Segoe UI", Font.BOLD, 14));
    h.setBackground(Color.WHITE);
    h.setForeground(Color.BLACK);
    h.setOpaque(true);
    h.setPreferredSize(new Dimension(0, 40));

    h.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(
            JTable t, Object value, boolean selected,
            boolean focus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                t, value, selected, focus, row, column);

        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBorder(
    javax.swing.BorderFactory.createMatteBorder(
        0, 0, 1, 1,
        new Color(220, 220, 220)
    )
);

        return label;
    }
});
    }

    public static void info(Component p, String m) {
        JOptionPane.showMessageDialog(p, m, "Informasi FIBER", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void warn(Component p, String m) {
        JOptionPane.showMessageDialog(p, m, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }
    public static void error(Component p, String m) {
        JOptionPane.showMessageDialog(p, m, "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
    public static void dbError(Component p, SQLException e) {
        error(p, "Terjadi kesalahan database:\n" + e.getMessage());
    }
    public static boolean confirm(Component p, String m, String title) {
        return JOptionPane.showConfirmDialog(p, m, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static String html(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
    public static DefaultTableModel readOnly(String... cols) {
        return new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
    }
    public static DefaultTableModel reviewModel() {
        return readOnly("ID", "Nama", "Isi Review", "Upvote");
    }
    public static void fillReviews(DefaultTableModel m, List<Review> list) {
        m.setRowCount(0);
        for (Review r : list) m.addRow(new Object[]{r.id(), r.penulis(), r.isi(), r.upvote()});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        lblContoh = new javax.swing.JLabel();
        txtContoh = new javax.swing.JTextField();
        btnUtama = new javax.swing.JButton();
        btnSekunder = new javax.swing.JButton();
        lblPalet = new javax.swing.JLabel();
        lblWarnaMerah = new javax.swing.JLabel();
        lblWarnaBurgundy = new javax.swing.JLabel();
        lblWarnaCoral = new javax.swing.JLabel();
        lblWarnaLatar = new javax.swing.JLabel();

        setBackground(new java.awt.Color(231, 231, 231));

        lblJudul.setText("Tema Tampilan FIBER");
        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(27, 0, 1));

        lblSub.setText("Pratinjau warna dan komponen yang dipakai bersama oleh seluruh tampilan aplikasi.");
        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSub.setForeground(new java.awt.Color(119, 119, 119));

        lblContoh.setText("Contoh input");
        lblContoh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblContoh.setForeground(new java.awt.Color(27, 0, 1));

        txtContoh.setText("Contoh isi kolom");
        txtContoh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContoh.setForeground(new java.awt.Color(27, 0, 1));
        txtContoh.setBackground(new java.awt.Color(255, 255, 255));
        txtContoh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)));

        btnUtama.setText("Tombol Utama");
        btnUtama.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnUtama.setForeground(new java.awt.Color(255, 255, 255));
        btnUtama.setBackground(new java.awt.Color(227, 5, 18));
        btnUtama.setFocusPainted(false);
        btnUtama.setBorderPainted(false);
        btnUtama.setOpaque(true);
        btnUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));

        btnSekunder.setText("Tombol Sekunder");
        btnSekunder.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSekunder.setForeground(new java.awt.Color(124, 3, 8));
        btnSekunder.setBackground(new java.awt.Color(255, 255, 255));
        btnSekunder.setFocusPainted(false);
        btnSekunder.setBorderPainted(false);
        btnSekunder.setOpaque(true);
        btnSekunder.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));

        lblPalet.setText("Palet warna");
        lblPalet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPalet.setForeground(new java.awt.Color(27, 0, 1));

        lblWarnaMerah.setText("Merah");
        lblWarnaMerah.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblWarnaMerah.setForeground(new java.awt.Color(255, 255, 255));
        lblWarnaMerah.setBackground(new java.awt.Color(227, 5, 18));
        lblWarnaMerah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWarnaMerah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        lblWarnaMerah.setOpaque(true);

        lblWarnaBurgundy.setText("Burgundy");
        lblWarnaBurgundy.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblWarnaBurgundy.setForeground(new java.awt.Color(255, 255, 255));
        lblWarnaBurgundy.setBackground(new java.awt.Color(124, 3, 8));
        lblWarnaBurgundy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWarnaBurgundy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        lblWarnaBurgundy.setOpaque(true);

        lblWarnaCoral.setText("Coral");
        lblWarnaCoral.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblWarnaCoral.setForeground(new java.awt.Color(255, 255, 255));
        lblWarnaCoral.setBackground(new java.awt.Color(246, 81, 77));
        lblWarnaCoral.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWarnaCoral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        lblWarnaCoral.setOpaque(true);

        lblWarnaLatar.setText("Latar");
        lblWarnaLatar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblWarnaLatar.setForeground(new java.awt.Color(27, 0, 1));
        lblWarnaLatar.setBackground(new java.awt.Color(231, 231, 231));
        lblWarnaLatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWarnaLatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        lblWarnaLatar.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblSub, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblContoh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtContoh, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnUtama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnSekunder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblPalet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWarnaMerah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblWarnaBurgundy, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblWarnaCoral, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblWarnaLatar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblJudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblSub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblContoh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtContoh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUtama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSekunder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(lblPalet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWarnaMerah, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWarnaBurgundy, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWarnaCoral, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWarnaLatar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSekunder;
    private javax.swing.JButton btnUtama;
    private javax.swing.JLabel lblContoh;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblPalet;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblWarnaBurgundy;
    private javax.swing.JLabel lblWarnaCoral;
    private javax.swing.JLabel lblWarnaLatar;
    private javax.swing.JLabel lblWarnaMerah;
    private javax.swing.JTextField txtContoh;
    // End of variables declaration//GEN-END:variables
}
