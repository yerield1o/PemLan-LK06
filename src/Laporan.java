import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Laporan extends JFrame {

    private JTable tableLaporan;
    private DefaultTableModel tableModel;
    private JLabel lblJudul;

    private String namaFileTransaksi = "transaksi.txt";
    private String namaFileBuku = "buku.txt";
    private String namaFileSiswa = "siswa.txt";

    public Laporan() {
        setTitle("Menu Laporan Perpustakaan");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelAtas = new JPanel();
        panelAtas.setLayout(new BorderLayout());

        lblJudul = new JLabel("Pilih Jenis Laporan di Bawah", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTombol = new JPanel();
        JButton btnBelumKembali = new JButton("1. Laporan Buku Belum Dikembalikan");
        JButton btnJatuhTempo = new JButton("2. Laporan Peminjam Lewat Jatuh Tempo");
        
        panelTombol.add(btnBelumKembali);
        panelTombol.add(btnJatuhTempo);

        panelAtas.add(lblJudul, BorderLayout.NORTH);
        panelAtas.add(panelTombol, BorderLayout.CENTER);
        add(panelAtas, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableLaporan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableLaporan);
        add(scrollPane, BorderLayout.CENTER);

        btnBelumKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bukuBelumDikembalikan();
            }
        });

        btnJatuhTempo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lewatJatuhTempo();
            }
        });
    }

    private void bukuBelumDikembalikan() {
        lblJudul.setText("=== Laporan Buku Belum Dikembalikan ===");

        String[] headerKolom = {"Kode Buku", "Nama Buku", "NIS Peminjam", "Tanggal Pinjam"};
        tableModel.setColumnIdentifiers(headerKolom);
        tableModel.setRowCount(0);

        try {
            List<String> transaksi = FileHandling.readFile(namaFileTransaksi);
            List<String> buku = FileHandling.readFile(namaFileBuku); 

            boolean ditemukan = false;

            for (String t : transaksi) {
                String[] info = t.split(",");
                if (info.length == 6 && info[5].equals("0")) {
                    ditemukan = true;
                    String namaBuku = getDetail(buku, info[2]);

                    Object[] barisData = {info[2], namaBuku, info[1], info[3]};
                    tableModel.addRow(barisData);
                }
            }

            if (!ditemukan) {
                JOptionPane.showMessageDialog(this, "Tidak ada data. Semua buku saat ini telah dikembalikan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan membaca file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void lewatJatuhTempo() {
        lblJudul.setText("=== Laporan Peminjam Melebihi Jatuh Tempo ===");

        String[] headerKolom = {"NIS", "Nama Siswa", "Kode Buku", "Tanggal Jatuh Tempo"};
        tableModel.setColumnIdentifiers(headerKolom);
        tableModel.setRowCount(0);

        try {
            List<String> transaksi = FileHandling.readFile(namaFileTransaksi);
            List<String> siswa = FileHandling.readFile(namaFileSiswa);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate hariIni = LocalDate.now();
            boolean ditemukan = false;

            for (String t : transaksi) {
                String[] info = t.split(",");
                if (info.length == 6 && info[5].equals("0")) {
                    try {
                        LocalDate jatuhTempo = LocalDate.parse(info[4], formatter);

                        if (hariIni.isAfter(jatuhTempo)) {
                            ditemukan = true;
                            String namaSiswa = getDetail(siswa, info[1]);

                            Object[] barisData = {info[1], namaSiswa, info[2], info[4]};
                            tableModel.addRow(barisData);
                        }
                    } catch (Exception e) {
                        System.out.println("Format tanggal salah pada kode transaksi: " + info[0]);
                    }
                }
            }

            if (!ditemukan) {
                JOptionPane.showMessageDialog(this, "Aman! Tidak ada peminjaman yang melewati tanggal jatuh tempo.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan membaca file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getDetail(List<String> listData, String idTarget) {
        for (String data : listData) {
            String[] info = data.split(",");
            if (info.length >= 2 && info[0].equals(idTarget)) {
                return info[1];
            }
        }
        return "Data tidak ditemukan";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Laporan().setVisible(true);
        });
    }
}