import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagerSiswa {
    public static String namaFile = "siswa.txt";

    public static void addSiswa() {
        JTextField nisField = new JTextField(15);
        JTextField namaField = new JTextField(15);
        JTextField alamatField = new JTextField(15);

        JPanel myPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        myPanel.add(new JLabel("NIS:"));
        myPanel.add(nisField);
        myPanel.add(new JLabel("Nama:"));
        myPanel.add(namaField);
        myPanel.add(new JLabel("Alamat:"));
        myPanel.add(alamatField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Tambah Siswa Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nis = nisField.getText().trim();
            String nama = namaField.getText().trim();
            String alamat = alamatField.getText().trim();

            if (nis.isEmpty() || nama.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String siswa = nis + "," + nama + "," + alamat;
            FileHandling.writeFile(namaFile, siswa, true);
            JOptionPane.showMessageDialog(null, "Siswa berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void viewSiswa() {
        List<String> siswa = FileHandling.readFile(namaFile);

        if (siswa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data siswa.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"NIS", "Nama", "Alamat"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String k : siswa) {
            String[] info = k.split(",");
            if (info.length == 3) {
                model.addRow(info);
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Daftar Siswa", JOptionPane.PLAIN_MESSAGE);
    }

    public static void updateSiswa() {
        String nis = JOptionPane.showInputDialog(null, "Masukkan NIS siswa yang ingin diperbarui:",
                "Cari Siswa", JOptionPane.QUESTION_MESSAGE);

        if (nis == null || nis.trim().isEmpty()) return;

        List<String> siswa = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = 0; i < siswa.size(); i++) {
            String[] info = siswa.get(i).split(",");
            if (info.length == 3 && info[0].equals(nis.trim())) {
                ditemukan = true;

                JTextField namaField = new JTextField(info[1], 15);
                JTextField alamatField = new JTextField(info[2], 15);

                JPanel myPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                myPanel.add(new JLabel("Nama Baru:"));
                myPanel.add(namaField);
                myPanel.add(new JLabel("Alamat Baru:"));
                myPanel.add(alamatField);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Perbarui Data Siswa (NIS: " + info[0] + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String namaBaru = namaField.getText().trim();
                    String alamatBaru = alamatField.getText().trim();

                    if (namaBaru.isEmpty() || alamatBaru.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nama dan Alamat tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String siswaBaru = info[0] + "," + namaBaru + "," + alamatBaru;
                    siswa.set(i, siswaBaru);
                    FileHandling.rewriteFile(namaFile, siswa);
                    JOptionPane.showMessageDialog(null, "Siswa berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Siswa dengan NIS " + nis + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteSiswa() {
        String nis = JOptionPane.showInputDialog(null, "Masukkan NIS siswa yang ingin dihapus:",
                "Hapus Siswa", JOptionPane.QUESTION_MESSAGE);

        if (nis == null || nis.trim().isEmpty()) return;

        List<String> siswa = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = siswa.size() - 1; i >= 0; i--) {
            String[] info = siswa.get(i).split(",");
            if (info.length == 3 && info[0].equals(nis.trim())) {
                ditemukan = true;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus siswa:\nNama: " + info[1],
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    siswa.remove(i);
                    FileHandling.rewriteFile(namaFile, siswa);
                    JOptionPane.showMessageDialog(null, "Siswa berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Siswa dengan NIS " + nis + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}