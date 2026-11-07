package com.mycompany.feedbackfiber.view;

import com.mycompany.feedbackfiber.controller.AuthController;
import com.mycompany.feedbackfiber.model.IdentityMethod;
import com.mycompany.feedbackfiber.model.Validator;
import com.mycompany.feedbackfiber.model.WilayahService;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import java.util.List;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComponent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * Halaman registrasi pelanggan. Desain dua kolom dibuat di NetBeans GUI Builder (RegisterView.form).
 */
public class RegisterView extends javax.swing.JFrame {

    private final AuthController auth = new AuthController();
    private IdentityMethod metode = IdentityMethod.AKUN_FIBER;
    private final WilayahService wilayahService = new WilayahService();
    private boolean memuatWilayah = false;
    /** Pemilih tanggal lahir (LGoodDatePicker); hasilnya langsung LocalDate sehingga cocok dengan kolom DATE di database. */
    private DatePicker dpTanggal;

    public RegisterView() {
        super("FIBER | Registrasi Pelanggan");
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

        siapkanTanggalLahir();

        ButtonGroup grup = new ButtonGroup();
        JRadioButton[] pilihan = {rbAkun, rbEmail, rbTelepon};
        IdentityMethod[] daftar = IdentityMethod.values();
        for (int i = 0; i < pilihan.length; i++) {
            final IdentityMethod m = daftar[i];
            pilihan[i].setText(m.label);
            pilihan[i].setSelected(m == metode);
            grup.add(pilihan[i]);
        }
        lblIdentitas.setText(metode.fieldLabel);
        siapkanDomisili();
        cmbGender.removeAllItems();
        cmbGender.addItem("Laki-Laki");
        cmbGender.addItem("Perempuan");
        aturTampilanForm();

        UI.hand(btnKembali, btnDaftar, rbAkun, rbEmail, rbTelepon);
    }


    /**
     * Pemilih tanggal lahir berbasis kalender. Tampilan memakai format dd-MM-yyyy (mudah dibaca),
     * sedangkan nilai yang diambil adalah LocalDate yang disimpan ke database sebagai DATE (yyyy-MM-dd).
     * Batas tanggal (usia minimal dan tahun paling awal) diambil dari {@link Validator}.
     */
    private void siapkanTanggalLahir() {
        DatePickerSettings pengaturan = new DatePickerSettings(new Locale("id", "ID"));
        pengaturan.setFormatForDatesCommonEra("dd-MM-yyyy");
        pengaturan.setAllowEmptyDates(true);
        pengaturan.setFontValidDate(UI.BODY);
        pengaturan.setFontInvalidDate(UI.BODY);
        pengaturan.setFontVetoedDate(UI.BODY);
        pengaturan.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, UI.RED);

        dpTanggal = new DatePicker(pengaturan);
        // Batas harus dipasang SETELAH DatePicker dibuat (syarat LGoodDatePicker).
        // Hanya tanggal yang memenuhi usia minimal dan tidak terlalu lampau yang dapat dipilih.
        pengaturan.setDateRangeLimits(Validator.tanggalLahirTerlama(), Validator.tanggalLahirTerakhir());
        dpTanggal.getComponentDateTextField().setBorder(BorderFactory.createEmptyBorder(7, 10, 7, 4));
        dpTanggal.getComponentDateTextField().setToolTipText("Pilih dari kalender atau ketik, contoh: 17-08-2005");
        dpTanggal.getComponentToggleCalendarButton().setText("Pilih");
        dpTanggal.getComponentToggleCalendarButton().setToolTipText("Buka kalender");

        pnlTanggal.setLayout(new BorderLayout());
        pnlTanggal.add(dpTanggal, BorderLayout.CENTER);
    }

    private void siapkanDomisili() {
        cmbProvinsi.setEnabled(false);
        cmbKabupaten.setEnabled(false);
        cmbKecamatan.setEnabled(false);
        muatWilayah("provinsi", wilayahService::getProvinsi, cmbProvinsi);
    }

    /** Menyusun ulang form agar seluruh komponen input memiliki kotak yang terlihat dan tidak bertumpuk. */
    private void aturTampilanForm() {
        Color teks = new Color(27, 0, 1);
        Color garis = new Color(205, 205, 205);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel isi = new JPanel();
        isi.setBackground(Color.WHITE);
        isi.setBorder(BorderFactory.createEmptyBorder(28, 34, 28, 34));
        isi.setLayout(new javax.swing.BoxLayout(isi, javax.swing.BoxLayout.Y_AXIS));

        lblJudul.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(lblJudul);
        isi.add(javax.swing.Box.createVerticalStrut(7));
        isi.add(lblSub);
        isi.add(javax.swing.Box.createVerticalStrut(22));

        lblMetode.setFont(labelFont);
        lblMetode.setForeground(teks);
        lblMetode.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(lblMetode);
        JPanel pilihanMetode = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        pilihanMetode.setOpaque(false);
        pilihanMetode.setAlignmentX(Component.LEFT_ALIGNMENT);
        pilihanMetode.add(rbAkun);
        pilihanMetode.add(rbEmail);
        pilihanMetode.add(rbTelepon);
        isi.add(pilihanMetode);
        isi.add(javax.swing.Box.createVerticalStrut(10));

        aturInput(txtNama, inputFont, garis);
        aturInput(pnlTanggal, inputFont, garis);
        pnlTanggal.setBorder(BorderFactory.createLineBorder(garis, 1));
        aturInput(cmbGender, inputFont, garis);
        aturInput(txtIdentitas, inputFont, garis);
        aturInput(txtPassword, inputFont, garis);
        aturInput(cmbProvinsi, inputFont, garis);
        aturInput(cmbKabupaten, inputFont, garis);
        aturInput(cmbKecamatan, inputFont, garis);

        tambahField(isi, lblNama, txtNama, labelFont, teks);
        tambahField(isi, lblTanggal, pnlTanggal, labelFont, teks);
        tambahField(isi, lblGender, cmbGender, labelFont, teks);
        tambahField(isi, lblIdentitas, txtIdentitas, labelFont, teks);
        tambahField(isi, lblPassword, txtPassword, labelFont, teks);

        lblDomisili.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDomisili.setForeground(teks);
        lblDomisili.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(javax.swing.Box.createVerticalStrut(8));
        isi.add(lblDomisili);
        isi.add(javax.swing.Box.createVerticalStrut(8));
        tambahField(isi, lblProvinsi, cmbProvinsi, labelFont, teks);
        tambahField(isi, lblKabupaten, cmbKabupaten, labelFont, teks);
        tambahField(isi, lblKecamatan, cmbKecamatan, labelFont, teks);

        pnlTombol.setOpaque(false);
        pnlTombol.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        pnlTombol.setPreferredSize(new Dimension(420, 44));
        pnlTombol.setAlignmentX(Component.LEFT_ALIGNMENT);
        isi.add(javax.swing.Box.createVerticalStrut(12));
        isi.add(pnlTombol);

        JScrollPane scroll = new JScrollPane(isi,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        pnlRight.removeAll();
        pnlRight.setLayout(new BorderLayout());
        pnlRight.add(scroll, BorderLayout.CENTER);
        pnlRight.revalidate();
        pnlRight.repaint();
    }

    private void aturInput(JComponent komponen, Font font, Color garis) {
        komponen.setFont(font);
        komponen.setForeground(new Color(27, 0, 1));
        komponen.setBackground(Color.WHITE);
        komponen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(garis, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
        komponen.setPreferredSize(new Dimension(420, 40));
        komponen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        komponen.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void tambahField(JPanel induk, JLabel label, JComponent input,
            Font font, Color warnaTeks) {
        label.setFont(font);
        label.setForeground(warnaTeks);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        induk.add(label);
        induk.add(javax.swing.Box.createVerticalStrut(5));
        induk.add(input);
        induk.add(javax.swing.Box.createVerticalStrut(12));
    }

    @FunctionalInterface private interface PemuatanWilayah { List<WilayahService.Wilayah> ambil() throws Exception; }

    private void muatWilayah(String jenis, PemuatanWilayah pemuatan, JComboBox<WilayahService.Wilayah> tujuan) {
        tujuan.setEnabled(false);
        tujuan.removeAllItems();
        tujuan.addItem(new WilayahService.Wilayah("", "Pilih " + jenis));
        new SwingWorker<List<WilayahService.Wilayah>, Void>() {
            @Override protected List<WilayahService.Wilayah> doInBackground() throws Exception { return pemuatan.ambil(); }
            @Override protected void done() {
                try {
                    List<WilayahService.Wilayah> data = get();
                    memuatWilayah = true;
                    tujuan.removeAllItems();
                    tujuan.addItem(new WilayahService.Wilayah("", "Pilih " + jenis));
                    for (WilayahService.Wilayah w : data) tujuan.addItem(w);
                    tujuan.setSelectedIndex(0);
                    memuatWilayah = false;
                    tujuan.setEnabled(true);
                    if (data.isEmpty()) UI.warn(RegisterView.this, "Data " + jenis + " tidak ditemukan.");
                } catch (Exception ex) {
                    memuatWilayah = false;
                    tujuan.setEnabled(false);
                    UI.warn(RegisterView.this, "Gagal memuat data " + jenis + ". Periksa koneksi internet.\n" + ex.getMessage());
                }
            }
        }.execute();
    }

    private void resetCombo(JComboBox<WilayahService.Wilayah> combo, String placeholder) {
        memuatWilayah = true;
        combo.removeAllItems();
        String jenis = combo == cmbKabupaten ? "Kota/Kabupaten" : "Kecamatan";
        combo.addItem(new WilayahService.Wilayah("", "Pilih " + jenis));
        combo.setSelectedIndex(0);
        combo.setEnabled(false);
        memuatWilayah = false;
    }

    private String ambilDomisili() {
        WilayahService.Wilayah prov = (WilayahService.Wilayah) cmbProvinsi.getSelectedItem();
        WilayahService.Wilayah kab = (WilayahService.Wilayah) cmbKabupaten.getSelectedItem();
        WilayahService.Wilayah kec = (WilayahService.Wilayah) cmbKecamatan.getSelectedItem();
        if (prov == null || kab == null || kec == null
                || prov.kode().isBlank() || kab.kode().isBlank() || kec.kode().isBlank()) return null;
        return kec.nama() + ", " + kab.nama() + ", " + prov.nama();
    }

    private void kembali() {
        new LoginView().setVisible(true);
        dispose();
    }

    private void daftar() {
        LocalDate lahir = dpTanggal.getDate();
        if (lahir == null) {
            UI.warn(this, dpTanggal.getText().isBlank()
                    ? "Tanggal lahir wajib dipilih."
                    : "Tanggal lahir tidak valid. Pilih dari kalender atau ketik dengan format hh-bb-tttt "
                    + "(contoh: 17-08-2005), usia minimal " + Validator.MIN_USIA + " tahun.");
            return;
        }
        if (!Validator.usiaMinimalTerpenuhi(lahir)) {
            UI.warn(this, "Usia minimal untuk mendaftar adalah " + Validator.MIN_USIA + " tahun.");
            return;
        }
        try {
            AuthController.RegisterResult r = auth.register(txtNama.getText(), lahir,
                    (String) cmbGender.getSelectedItem(), metode, txtIdentitas.getText(),
                    new String(txtPassword.getPassword()), ambilDomisili());
            switch (r.status()) {
                case DATA_TIDAK_VALID -> UI.warn(this, r.pesan());
                case IDENTITAS_DIGUNAKAN -> {
                    UI.warn(this, r.pesan());
                    if (UI.confirm(this, "Kembali ke halaman utama?", "Buat Akun")) kembali();
                }
                case OK -> {
                    UI.info(this, r.pesan());
                    kembali();
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
        lblMetode = new javax.swing.JLabel();
        rbAkun = new javax.swing.JRadioButton();
        rbEmail = new javax.swing.JRadioButton();
        rbTelepon = new javax.swing.JRadioButton();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblTanggal = new javax.swing.JLabel();
        pnlTanggal = new javax.swing.JPanel();
        lblGender = new javax.swing.JLabel();
        cmbGender = new javax.swing.JComboBox<>();
        lblIdentitas = new javax.swing.JLabel();
        txtIdentitas = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblDomisili = new javax.swing.JLabel();
        lblProvinsi = new javax.swing.JLabel();
        cmbProvinsi = new javax.swing.JComboBox<>();
        cmbProvinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProvinsiActionPerformed(evt);
            }
        });
        lblKabupaten = new javax.swing.JLabel();
        cmbKabupaten = new javax.swing.JComboBox<>();
        cmbKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKabupatenActionPerformed(evt);
            }
        });
        lblKecamatan = new javax.swing.JLabel();
        cmbKecamatan = new javax.swing.JComboBox<>();
        pnlTombol = new javax.swing.JPanel();
        btnKembali = new javax.swing.JButton();
        btnDaftar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(27, 0, 1));
        lblJudul.setText("Buat Akun Pelanggan");

        lblSub.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSub.setForeground(new java.awt.Color(119, 119, 119));
        lblSub.setText("<html>Lengkapi informasi berikut untuk mulai memberikan feedback kepada FIBER.</html>");
        lblSub.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblMetode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMetode.setForeground(new java.awt.Color(27, 0, 1));
        lblMetode.setText("Metode identitas akun");

        rbAkun.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbAkun.setForeground(new java.awt.Color(27, 0, 1));
        rbAkun.setOpaque(false);
        rbAkun.setContentAreaFilled(false);
        rbAkun.setSelected(true);
        rbAkun.setText("Akun FIBER");
        rbAkun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbAkunActionPerformed(evt);
            }
        });

        rbEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbEmail.setForeground(new java.awt.Color(27, 0, 1));
        rbEmail.setOpaque(false);
        rbEmail.setContentAreaFilled(false);
        rbEmail.setText("Alamat Email");
        rbEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbEmailActionPerformed(evt);
            }
        });

        rbTelepon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbTelepon.setForeground(new java.awt.Color(27, 0, 1));
        rbTelepon.setOpaque(false);
        rbTelepon.setContentAreaFilled(false);
        rbTelepon.setText("Nomor Telepon");
        rbTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTeleponActionPerformed(evt);
            }
        });

        lblNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNama.setForeground(new java.awt.Color(27, 0, 1));
        lblNama.setText("Nama lengkap");

        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNama.setForeground(new java.awt.Color(27, 0, 1));
        txtNama.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(27, 0, 1));
        lblTanggal.setText("Tanggal lahir");

        pnlTanggal.setOpaque(false);
        pnlTanggal.setLayout(new java.awt.BorderLayout());

        lblGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGender.setForeground(new java.awt.Color(27, 0, 1));
        lblGender.setText("Jenis kelamin");

        cmbGender.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbGender.setForeground(new java.awt.Color(27, 0, 1));
        cmbGender.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblIdentitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdentitas.setForeground(new java.awt.Color(27, 0, 1));
        lblIdentitas.setText("Username akun FIBER");

        txtIdentitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIdentitas.setForeground(new java.awt.Color(27, 0, 1));
        txtIdentitas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(27, 0, 1));
        lblPassword.setText("Password (min. 8 karakter)");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(27, 0, 1));
        txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        lblDomisili.setText("Domisili");

        lblProvinsi.setText("Provinsi");

        lblKabupaten.setText("Kota/Kabupaten");

        lblKecamatan.setText("Kecamatan");

        pnlTombol.setOpaque(false);
        pnlTombol.setLayout(new java.awt.GridLayout(1, 2, 12, 0));

        btnKembali.setText("Kembali ke Login");
        btnKembali.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnKembali.setForeground(new java.awt.Color(124, 3, 8));
        btnKembali.setFocusPainted(false);
        btnKembali.setBorderPainted(false);
        btnKembali.setOpaque(true);
        btnKembali.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });
        pnlTombol.add(btnKembali);

        btnDaftar.setText("Daftar");
        btnDaftar.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDaftar.setForeground(new java.awt.Color(255, 255, 255));
        btnDaftar.setBackground(new java.awt.Color(227, 5, 18));
        btnDaftar.setFocusPainted(false);
        btnDaftar.setBorderPainted(false);
        btnDaftar.setOpaque(true);
        btnDaftar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 18, 10, 18));
        btnDaftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaftarActionPerformed(evt);
            }
        });
        pnlTombol.add(btnDaftar);

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblJudul))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblSub, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                .addGap(42, 42, 42))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblMetode))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(rbAkun, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(rbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(rbTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(txtNama)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(pnlTanggal)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblGender, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(cmbGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblIdentitas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(txtIdentitas)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(txtPassword)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblDomisili))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProvinsi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblKabupaten, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbKabupaten, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbKecamatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(pnlTombol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblJudul))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(lblSub, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(lblMetode))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(rbAkun, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(rbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(rbTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(pnlTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(lblGender, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(lblIdentitas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(txtIdentitas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(684, 684, 684)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(704, 704, 704)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(465, 465, 465)
                .addComponent(lblDomisili))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(505, 505, 505)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProvinsi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(569, 569, 569)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKabupaten, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKabupaten, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addGap(633, 633, 633)
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKecamatan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRightLayout.createSequentialGroup()
                .addGap(0, 698, Short.MAX_VALUE)
                .addComponent(pnlTombol, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        getContentPane().add(pnlRight);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void rbAkunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbAkunActionPerformed
        metode = IdentityMethod.AKUN_FIBER;
        lblIdentitas.setText(metode.fieldLabel);
        txtIdentitas.setText("");
    }//GEN-LAST:event_rbAkunActionPerformed

    private void rbEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbEmailActionPerformed
        metode = IdentityMethod.EMAIL;
        lblIdentitas.setText(metode.fieldLabel);
        txtIdentitas.setText("");
    }//GEN-LAST:event_rbEmailActionPerformed

    private void rbTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTeleponActionPerformed
        metode = IdentityMethod.TELEPON;
        lblIdentitas.setText(metode.fieldLabel);
        txtIdentitas.setText("");
    }//GEN-LAST:event_rbTeleponActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        kembali();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
        daftar();
    }//GEN-LAST:event_btnDaftarActionPerformed

    private void cmbProvinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProvinsiActionPerformed
        if (memuatWilayah) return;
        WilayahService.Wilayah prov = (WilayahService.Wilayah) cmbProvinsi.getSelectedItem();
        resetCombo(cmbKabupaten, "Pilih Kota/Kabupaten");
        resetCombo(cmbKecamatan, "Pilih Kecamatan");
        if (prov != null && !prov.kode().isBlank()) muatWilayah("kabupaten", () -> wilayahService.getKabupaten(prov.kode()), cmbKabupaten);
    }//GEN-LAST:event_cmbProvinsiActionPerformed

    private void cmbKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKabupatenActionPerformed
        if (memuatWilayah) return;
        WilayahService.Wilayah kab = (WilayahService.Wilayah) cmbKabupaten.getSelectedItem();
        resetCombo(cmbKecamatan, "Pilih Kecamatan");
        if (kab != null && !kab.kode().isBlank()) muatWilayah("kecamatan", () -> wilayahService.getKecamatan(kab.kode()), cmbKecamatan);
    }//GEN-LAST:event_cmbKabupatenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnKembali;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<WilayahService.Wilayah> cmbKabupaten;
    private javax.swing.JComboBox<WilayahService.Wilayah> cmbKecamatan;
    private javax.swing.JComboBox<WilayahService.Wilayah> cmbProvinsi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDomisili;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblIdentitas;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKabupaten;
    private javax.swing.JLabel lblKecamatan;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMetode;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblProvinsi;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JPanel pnlBrand;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlTanggal;
    private javax.swing.JPanel pnlTombol;
    private javax.swing.JRadioButton rbAkun;
    private javax.swing.JRadioButton rbEmail;
    private javax.swing.JRadioButton rbTelepon;
        private javax.swing.JTextField txtIdentitas;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
