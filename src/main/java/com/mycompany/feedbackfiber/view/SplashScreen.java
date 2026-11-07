
package com.mycompany.feedbackfiber.view;

public class SplashScreen extends javax.swing.JFrame {

    private int progress = 0;

    public SplashScreen() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
                g2.setPaint(new java.awt.GradientPaint(
                    0, 0, new java.awt.Color(220, 20, 35),
                    getWidth(), getHeight(),
                    new java.awt.Color(155, 10, 25)
                ));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lblLogo = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(220, 20, 35));
        setTitle("FIBER - Customer Feedback System");
        setResizable(false);
        setUndecorated(true);

        panelBackground.setBackground(new java.awt.Color(220, 20, 35));

        lblLogo.setFont(new java.awt.Font("Poppins", 1, 58)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("FIBER");

        lblSubtitle.setFont(new java.awt.Font("Poppins", 1, 19)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(255, 255, 255));
        lblSubtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtitle.setText("Customer Feedback System");

        progressBar.setBackground(new java.awt.Color(130, 15, 25));
        progressBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        progressBar.setForeground(new java.awt.Color(255, 255, 255));

        statusLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        statusLabel.setForeground(new java.awt.Color(255, 255, 255));
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Memuat aplikasi...");

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(205, 205, 205)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelBackgroundLayout.setVerticalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(lblSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(351, 351, 351)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Menjalankan Splash Screen
    public void showSplash(Runnable selesai) {

        setVisible(true);

        javax.swing.Timer timer = new javax.swing.Timer(30, null);

        timer.addActionListener(e -> {

            progress++;
            progressBar.setValue(progress);

            if (progress < 35) {
                statusLabel.setText("Menyiapkan aplikasi...");
            } 
            else if (progress < 75) {
                statusLabel.setText("Menghubungkan layanan...");
            } 
            else {
                statusLabel.setText("Hampir selesai...");
            }

            if (progress >= 100) {

                timer.stop();
                dispose();

                selesai.run();
            }
        });

        timer.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JPanel panelBackground;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}
