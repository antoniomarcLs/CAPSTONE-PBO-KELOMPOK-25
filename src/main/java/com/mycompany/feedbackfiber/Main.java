package com.mycompany.feedbackfiber;

import com.mycompany.feedbackfiber.config.Koneksi;
import com.mycompany.feedbackfiber.view.LoginView;
import com.mycompany.feedbackfiber.view.LoadingScreen;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new LoadingScreen().showSplash(Main::mulai));
    }

    private static void mulai() {
        try (Connection c = Koneksi.open()) {
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Database belum berhasil terhubung.\nPeriksa pengaturan database.\n\n" + e.getMessage(),
                    "Feedback FIBER", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }
        new LoginView().setVisible(true);
    }
}