import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManagerStaff {
    public static String namaFile = "staff.txt";

    public static void addStaff() {
        JTextField nipField = new JTextField(15);
        JTextField namaField = new JTextField(15);
        JTextField tglLahirField = new JTextField(15);

        JPanel myPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        myPanel.add(new JLabel("NIP Staff:"));
        myPanel.add(nipField);
        myPanel.add(new JLabel("Nama Staff:"));
        myPanel.add(namaField);
        myPanel.add(new JLabel("Tanggal Lahir (DD-MM-YYYY):"));
        myPanel.add(tglLahirField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Tambah Pegawai Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nip = nipField.getText().trim();
            String nama = namaField.getText().trim();
            String tglLahir = tglLahirField.getText().trim();

            if (nip.isEmpty() || nama.isEmpty() || tglLahir.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String staff = nip + "," + nama + "," + tglLahir;
            FileHandling.writeFile(namaFile, staff, true);
            JOptionPane.showMessageDialog(null, "Pegawai berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void viewStaff() {
        List<String> staff = FileHandling.readFile(namaFile);

        if (staff.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data staff.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"NIP Staff", "Nama Staff", "Tanggal Lahir"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String k : staff) {
            String[] info = k.split(",");
            if (info.length >= 3) {
                model.addRow(info);
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 200));

        JOptionPane.showMessageDialog(null, scrollPane, "Daftar Pegawai", JOptionPane.PLAIN_MESSAGE);
    }

    public static void updateStaff() {
        String nip = JOptionPane.showInputDialog(null, "Masukkan NIP staff yang ingin diperbarui:",
                "Cari Pegawai", JOptionPane.QUESTION_MESSAGE);

        if (nip == null || nip.trim().isEmpty()) return;

        List<String> staff = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = 0; i < staff.size(); i++) {
            String[] info = staff.get(i).split(",");
            if (info.length >= 3 && info[0].equals(nip.trim())) {
                ditemukan = true;

                JTextField namaField = new JTextField(info[1], 15);
                JTextField tglLahirField = new JTextField(info[2], 15);

                JPanel myPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                myPanel.add(new JLabel("Nama Baru:"));
                myPanel.add(namaField);
                myPanel.add(new JLabel("Tanggal Lahir Baru:"));
                myPanel.add(tglLahirField);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Perbarui Data Pegawai (NIP: " + info[0] + ")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String namaBaru = namaField.getText().trim();
                    String tglLahirBaru = tglLahirField.getText().trim();

                    if (namaBaru.isEmpty() || tglLahirBaru.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nama dan Tanggal Lahir tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String staffBaru = info[0] + "," + namaBaru + "," + tglLahirBaru;
                    staff.set(i, staffBaru);
                    FileHandling.rewriteFile(namaFile, staff);
                    JOptionPane.showMessageDialog(null, "Data pegawai berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Pegawai dengan NIP " + nip + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteStaff() {
        String nip = JOptionPane.showInputDialog(null, "Masukkan NIP staff yang ingin dihapus:",
                "Hapus Pegawai", JOptionPane.QUESTION_MESSAGE);

        if (nip == null || nip.trim().isEmpty()) return;

        List<String> staff = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = staff.size() - 1; i >= 0; i--) {
            String[] info = staff.get(i).split(",");

            if (info.length >= 3 && info[0].equals(nip.trim())) {
                ditemukan = true;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Apakah Anda yakin ingin menghapus pegawai:\nNama: " + info[1],
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    staff.remove(i);
                    FileHandling.rewriteFile(namaFile, staff);
                    JOptionPane.showMessageDialog(null, "Pegawai berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            }
        }

        if (!ditemukan) {
            JOptionPane.showMessageDialog(null, "Pegawai dengan NIP " + nip + " tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}