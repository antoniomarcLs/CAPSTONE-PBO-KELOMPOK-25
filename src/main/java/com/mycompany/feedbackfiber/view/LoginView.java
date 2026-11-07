package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.AuthController;
import com.mycompany.feedbackfiber.controller.Session;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Halaman login pelanggan FIBER. Desain dua kolom (panel branding merah di kiri dan
 * formulir putih di kanan) dibuat di NetBeans GUI Builder melalui file LoginView.form,
 * sama seperti RegisterView.
 */
public class LoginView extends javax.swing.JFrame {

    private final AuthController auth = new AuthController();

    public LoginView() {
        initComponents();
        siapkanKomponen();
        UI.ukuranHalaman(this);
    }

    private void siapkanKomponen() {
        // Panel branding merah: gradasi digambar lewat UI delegate agar tetap berupa JPanel biasa di form.
        pnlBrand.setUI(new javax.swing.plaf.basic.BasicPanelUI() {
            @Override
            public void update(java.awt.Graphics g, javax.swing.JComponent c) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setPaint(new java.awt.GradientPaint(0, 0, UI.RED, c.getWidth(), c.getHeight(), UI.BURGUNDY));
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                g2.setColor(new java.awt.Color(255, 255, 255, 22));
                g2.fillOval(-100, c.getHeight() - 240, 340, 340);
                g2.dispose();
                paint(g, c);
            }
        });

        aturInput(txtIdentitas);
        aturInput(txtPassword);
        UI.hand(btnMasuk, btnDaftar);

    }

    /** Menambahkan ruang dalam pada kotak input tanpa mengubah garis tepi dari desain. */
    private void aturInput(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(field.getBorder(),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));
    }

    /** Proses autentikasi asli dipertahankan. */
    private void doLogin() {
        String identitas = txtIdentitas.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (identitas.isEmpty() || password.isEmpty()) {
            UI.warn(this, "Identitas dan password wajib diisi!");
            return;
        }

        try {
            AuthController.LoginResult r = auth.login(identitas, password);
            switch (r.status()) {
                case BELUM_TERDAFTAR, PASSWORD_SALAH -> UI.error(this, "Username dan password salah!");
                case OK -> {
                    Session.set(r.user());
                    if (r.user().isAdmin()) {
                        new AdminMenuView().setVisible(true);
                    } else {
                        new UserMenuView().setVisible(true);
                    }
                    dispose();
                }
            }
        } catch (SQLException e) {
            UI.dbError(this, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBrand = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlRight = new javax.swing.JPanel();
        lblJudul = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        lblIdentitas = new javax.swing.JLabel();
        txtIdentitas = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnMasuk = new javax.swing.JButton();
        lblBelum = new javax.swing.JLabel();
        btnDaftar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FIBER | Login Pelanggan");
        getContentPane().setLayout(new java.awt.GridLayout(1, 2));

        pnlBrand.setBackground(new java.awt.Color(227, 5, 18));

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setText("FIBER");

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Customer Feedback System");

        javax.swing.GroupLayout pnlBrandLayout = new javax.swing.GroupLayout(pnlBrand);
        pnlBrand.setLayout(pnlBrandLayout);
        pnlBrandLayout.setHorizontalGroup(
            pnlBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBrandLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblLogo)
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlBrandLayout.setVerticalGroup(
            pnlBrandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBrandLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(pnlBrand);

        pnlRight.setBackground(new java.awt.Color(255, 255, 255));

        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(27, 0, 1));
        lblJudul.setText("Masuk ke Akun Anda");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSub.setForeground(new java.awt.Color(119, 119, 119));
        lblSub.setText("Masuk untuk memberikan feedback kepada FIBER.");

        lblIdentitas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblIdentitas.setForeground(new java.awt.Color(27, 0, 1));
        lblIdentitas.setText("Username / Email / No. HP");

        txtIdentitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdentitas.setForeground(new java.awt.Color(27, 0, 1));
        txtIdentitas.setToolTipText("Masukkan username, alamat email, atau nomor HP");
        txtIdentitas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)));
        txtIdentitas.setCaretColor(new java.awt.Color(227, 5, 18));

        lblPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(27, 0, 1));
        lblPassword.setText("Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(27, 0, 1));
        txtPassword.setToolTipText("Masukkan password akun Anda");
        txtPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215)));
        txtPassword.setCaretColor(new java.awt.Color(227, 5, 18));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnMasuk.setBackground(new java.awt.Color(227, 5, 18));
        btnMasuk.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMasuk.setForeground(new java.awt.Color(255, 255, 255));
        btnMasuk.setText("Masuk");
        btnMasuk.setBorderPainted(false);
        btnMasuk.setFocusPainted(false);
        btnMasuk.setOpaque(true);
        btnMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMasukMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMasukMouseExited(evt);
            }
        });
        btnMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasukActionPerformed(evt);
            }
        });

        lblBelum.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblBelum.setForeground(new java.awt.Color(27, 0, 1));
        lblBelum.setText("Belum memiliki akun?");

        btnDaftar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDaftar.setForeground(new java.awt.Color(227, 5, 18));
        btnDaftar.setText("Daftar di sini");
        btnDaftar.setBorderPainted(false);
        btnDaftar.setContentAreaFilled(false);
        btnDaftar.setFocusPainted(false);
        btnDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudul)
                    .addComponent(lblSub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIdentitas)
                    .addComponent(txtIdentitas)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword)
                    .addComponent(btnMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlRightLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblBelum)
                        .addGap(4, 4, 4)
                        .addComponent(btnDaftar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(48, 48, 48))
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblJudul)
                .addGap(8, 8, 8)
                .addComponent(lblSub)
                .addGap(38, 38, 38)
                .addComponent(lblIdentitas)
                .addGap(9, 9, 9)
                .addComponent(txtIdentitas, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(lblPassword)
                .addGap(9, 9, 9)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBelum)
                    .addComponent(btnDaftar))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(pnlRight);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMasukMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukMouseEntered
        btnMasuk.setBackground(new java.awt.Color(190, 4, 15));
    }//GEN-LAST:event_btnMasukMouseEntered

    private void btnMasukMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMasukMouseExited
        btnMasuk.setBackground(UI.RED);
    }//GEN-LAST:event_btnMasukMouseExited

    private void btnMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasukActionPerformed
        doLogin();
    }//GEN-LAST:event_btnMasukActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        doLogin();
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
        new RegisterView().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnDaftarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBelum;
    private javax.swing.JLabel lblIdentitas;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSub;
    private javax.swing.JPanel pnlBrand;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JTextField txtIdentitas;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
