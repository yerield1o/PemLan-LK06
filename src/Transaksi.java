import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Transaksi extends JFrame {
    public static String namaFile = "transaksi.txt";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public JTabbedPane tabbedPane;

    public Transaksi() {
        setTitle("Sistem Transaksi Perpustakaan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Peminjaman Buku", createPanelPinjam());
        tabbedPane.addTab("Pengembalian Buku", createPanelKembali());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createPanelPinjam() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Kode Transaksi:"));
        JTextField txtKode = new JTextField();
        panel.add(txtKode);
        
        panel.add(new JLabel("NIS Peminjam:"));
        JTextField txtNis = new JTextField();
        panel.add(txtNis);
        
        panel.add(new JLabel("Kode Buku:"));
        JTextField txtBuku = new JTextField();
        panel.add(txtBuku);
        
        JButton btnPinjam = new JButton("Pinjam Buku");
        btnPinjam.setBackground(new Color(52, 152, 219));
        btnPinjam.setForeground(Color.WHITE);
        
        panel.add(new JLabel("")); 
        panel.add(btnPinjam);
        
        btnPinjam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String kode = txtKode.getText().trim();
                    String nis = txtNis.getText().trim();
                    String kodeBuku = txtBuku.getText().trim();
                    
                    if(kode.isEmpty() || nis.isEmpty() || kodeBuku.isEmpty()){
                        JOptionPane.showMessageDialog(panel, "Harap isi semua kolom data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    int jumlah = hitungPinjaman(nis);
                    if (jumlah >= 2) {
                        JOptionPane.showMessageDialog(panel, "Siswa sudah meminjam maksimal 2 buku!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    String tglPinjam = LocalDate.now().format(formatter);
                    String tglKembali = LocalDate.now().plusDays(7).format(formatter);
                    String status = "0"; 
                    
                    String data = kode + "," + nis + "," + kodeBuku + "," + tglPinjam + "," + tglKembali + "," + status;
                    FileHandling.writeFile(namaFile, data, true);
                    
                    JOptionPane.showMessageDialog(panel, "Peminjaman berhasil dilakukan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    txtKode.setText("");
                    txtNis.setText("");
                    txtBuku.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Terjadi error saat peminjaman!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }
    
    private JPanel createPanelKembali() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 80, 20));
        
        panel.add(new JLabel("Kode Transaksi:"));
        JTextField txtKode = new JTextField();
        panel.add(txtKode);
        
        JButton btnKembali = new JButton("Kembalikan Buku");
        btnKembali.setBackground(new Color(46, 204, 113));
        btnKembali.setForeground(Color.WHITE);
        
        panel.add(new JLabel("")); 
        panel.add(btnKembali);
        
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String kode = txtKode.getText().trim();
                    if(kode.isEmpty()){
                        JOptionPane.showMessageDialog(panel, "Harap isi Kode Transaksi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    List<String> transaksi = FileHandling.readFile(namaFile);
                    boolean ditemukan = false;

                    for (int i = 0; i < transaksi.size(); i++) {
                        String[] info = transaksi.get(i).split(",");

                        if (info.length == 6 && info[0].equals(kode)) {
                            if (info[5].equals("1")) {
                                JOptionPane.showMessageDialog(panel, "Buku pada transaksi ini sudah dikembalikan sebelumnya!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }

                            info[5] = "1";
                            transaksi.set(i, String.join(",", info));
                            ditemukan = true;
                            break;
                        }
                    }

                    if (ditemukan) {
                        FileHandling.rewriteFile(namaFile, transaksi);
                        JOptionPane.showMessageDialog(panel, "Buku berhasil dikembalikan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        txtKode.setText("");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Kode transaksi tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Terjadi error saat pengembalian!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        return panel;
    }

    private static int hitungPinjaman(String nis) {
        int count = 0;
        List<String> transaksi = FileHandling.readFile(namaFile);

        for (String t : transaksi) {
            String[] info = t.split(",");
            if (info.length == 6 && info[1].equals(nis) && info[5].equals("0")) {
                count++;
            }
        }
        return count;
    }


    public static void pinjamBuku() {
        SwingUtilities.invokeLater(() -> {
            Transaksi gui = new Transaksi();
            gui.tabbedPane.setSelectedIndex(0); 
            gui.setVisible(true);
        });
    }

    public static void kembalikanBuku() {
        SwingUtilities.invokeLater(() -> {
            Transaksi gui = new Transaksi();
            gui.tabbedPane.setSelectedIndex(1); 
            gui.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Transaksi().setVisible(true);
        });
    }
}
