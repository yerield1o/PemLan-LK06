import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagerBuku {
    public static String namaFile = "buku.txt";

    public static void addBuku() {
        JTextField kodeField = new JTextField(15);
        JTextField namaField = new JTextField(15);
        JTextField jenisField = new JTextField(15);

        JPanel myPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        myPanel.add(new JLabel("Kode Buku:"));
        myPanel.add(kodeField);
        myPanel.add(new JLabel("Nama Buku:"));
        myPanel.add(namaField);
        myPanel.add(new JLabel("Jenis Buku:"));
        myPanel.add(jenisField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Tambah Buku Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String kode = kodeField.getText().trim();
            String nama = namaField.getText().trim();
            String jenis = jenisField.getText().trim();

            if (kode.isEmpty() || nama.isEmpty() || jenis.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String buku = kode + "," + nama + "," + jenis;
            FileHandling.writeFile(namaFile, buku, true);
            JOptionPane.showMessageDialog(null, "Buku berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void viewBuku() {
        List<String> buku = FileHandling.readFile(namaFile);

        if (buku.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data buku.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"Kode Buku", "Nama Buku", "Jenis Buku"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String k : buku) {
            String[] info = k.split(",");
            if (info.length >= 3) {
                model.addRow(info);
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Daftar Buku", JOptionPane.PLAIN_MESSAGE);
    }

    public static void updateBuku() {
        String kode = JOptionPane.showInputDialog(null, "Masukkan kode buku yang ingin diperbarui:",
                "Cari Buku", JOptionPane.QUESTION_MESSAGE);

        if (kode == null || kode.trim().isEmpty()) return;

        List<String> buku = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = 0; i < buku.size(); i++) {
            String[] info = buku.get(i).split(",");
            if (info.length >= 3 && info[0].equals(kode.trim())) {
                ditemukan = true;

                JTextField namaField = new JTextField(info[1], 15);
                JTextField jenisField = new JTextField(info[2], 15);

                JPanel myPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                myPanel.add(new JLabel("Nama Buku Baru:"));
                myPanel.add(namaField);
                myPanel.add(new JLabel("Jenis Buku Baru:"));
                myPanel.add(jenisField);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Perbarui Data Buku (Kode: " + info[0] + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String namaBaru = namaField.getText().trim();
                    String jenisBaru = jenisField.getText().trim();

                    if (namaBaru.isEmpty() || jenisBaru.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nama dan Jenis buku tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String bukuBaru = info[0] + "," + namaBaru + "," + jenisBaru;
                    buku.set(i, bukuBaru);
                    FileHandling.rewriteFile(namaFile, buku);
                    JOptionPane.showMessageDialog(null, "Buku berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Buku dengan kode " + kode + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteBuku() {
        String kode = JOptionPane.showInputDialog(null, "Masukkan kode buku yang ingin dihapus:",
                "Hapus Buku", JOptionPane.QUESTION_MESSAGE);

        if (kode == null || kode.trim().isEmpty()) return;

        List<String> buku = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = buku.size() - 1; i >= 0; i--) {
            String[] info = buku.get(i).split(",");

            if (info.length >= 3 && info[0].equals(kode.trim())) {
                ditemukan = true;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus buku:\nJudul: " + info[1],
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    buku.remove(i);
                    FileHandling.rewriteFile(namaFile, buku);
                    JOptionPane.showMessageDialog(null, "Buku berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Buku dengan kode " + kode + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}