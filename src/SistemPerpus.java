import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SistemPerpus {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private String nama = "";

    public SistemPerpus() {
        frame = new JFrame("Sistem Manajemen Perpustakaan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 400);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createMenuPanel(), "Menu");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("LOGIN");

        JLabel nipLabel = new JLabel("Masukkan NIP:");
        JTextField nipField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(nipLabel, gbc);

        gbc.gridx = 1;
        panel.add(nipField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String NIPlogin = nipField.getText().trim();
            boolean loginSuccess = false;

            try {
                List<String> staff = FileHandling.readFile("staff.txt");
                for (String s : staff) {
                    String[] info = s.split(",");
                    if (info.length >= 2 && info[0].equals(NIPlogin)) {
                        loginSuccess = true;
                        nama = info[1];
                        JOptionPane.showMessageDialog(frame, "Selamat Datang " + nama, "Success", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(cardPanel, "Menu");
                        break;
                    }
                }

                if (!loginSuccess) {
                    JOptionPane.showMessageDialog(frame, "NIP tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan saat membaca file staff.txt", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("MENU UTAMA", SwingConstants.CENTER);

        JButton btnSiswa = new JButton("1. Managemen Siswa");
        JButton btnBuku = new JButton("2. Managemen Buku");
        JButton btnStaff = new JButton("3. Managemen Staff");
        JButton btnTransaksi = new JButton("4. Peminjaman/Pengembalian");
        JButton btnLaporan = new JButton("5. Laporan");
        JButton btnKeluar = new JButton("6. Keluar");

        btnSiswa.addActionListener(e -> showSiswaMenu());
        btnBuku.addActionListener(e -> showBukuMenu());
        btnStaff.addActionListener(e -> showStaffMenu());
        btnTransaksi.addActionListener(e -> new Transaksi().setVisible(true));
        btnLaporan.addActionListener(e -> new Laporan().setVisible(true));

        btnKeluar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Keluar?", "Keluar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        panel.add(titleLabel);
        panel.add(btnSiswa);
        panel.add(btnBuku);
        panel.add(btnStaff);
        panel.add(btnTransaksi);
        panel.add(btnLaporan);
        panel.add(btnKeluar);

        return panel;
    }

    private void showSiswaMenu() {
        String[] options = {"Daftar Siswa", "Tambah Siswa", "Perbarui Siswa", "Hapus Siswa", "Batal"};
        int choice = JOptionPane.showOptionDialog(frame, "Pilih Aksi Managemen Siswa:", "Managemen Siswa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: ManagerSiswa.viewSiswa(); break;
            case 1: ManagerSiswa.addSiswa(); break;
            case 2: ManagerSiswa.updateSiswa(); break;
            case 3: ManagerSiswa.deleteSiswa(); break;
        }
    }

    private void showBukuMenu() {
        String[] options = {"Daftar Buku", "Tambah Buku", "Perbarui Buku", "Hapus Buku", "Batal"};
        int choice = JOptionPane.showOptionDialog(frame, "Pilih Aksi Managemen Buku:", "Managemen Buku",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: ManagerBuku.viewBuku(); break;
            case 1: ManagerBuku.addBuku(); break;
            case 2: ManagerBuku.updateBuku(); break;
            case 3: ManagerBuku.deleteBuku(); break;
        }
    }

    private void showStaffMenu() {
        String[] options = {"Daftar Pegawai", "Tambah Pegawai", "Perbarui Pegawai", "Hapus Pegawai", "Batal"};
        int choice = JOptionPane.showOptionDialog(frame, "Pilih Aksi Managemen Pegawai:", "Managemen Pegawai",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: ManagerStaff.viewStaff(); break;
            case 1: ManagerStaff.addStaff(); break;
            case 2: ManagerStaff.updateStaff(); break;
            case 3: ManagerStaff.deleteStaff(); break;
        }
    }

    private void showTransaksiMenu() {

    }

    private void showLaporanMenu() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemPerpus());
    }
}