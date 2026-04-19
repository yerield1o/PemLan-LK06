import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Laporan {
    public static String namaFileTransaksi = "transaksi.txt"; 
    private static Scanner scan = new Scanner(System.in);

    public static void pilihLaporan() {
        System.out.println("\n--- Pilih Jenis Laporan ---");
        System.out.println("1. Laporan buku belum dikembalikan");
        System.out.println("2. Laporan peminjam melebihi jatuh tempo");
        System.out.print("Pilihan: ");
        String pilihan = scan.nextLine();

        switch (pilihan) {
            case "1":
                bukuBelumDikembalikan();
                break;
            case "2":
                lewatJatuhTempo();
                break;
            default:
                System.out.println("Pilihan invalid");
        }
    }

    public static void bukuBelumDikembalikan() {
        List<String> transaksi = FileHandling.readFile(namaFileTransaksi);
        List<String> buku = FileHandling.readFile(ManagerBuku.namaFile);

        System.out.println("\n=== Laporan Buku Belum Dikembalikan ===");
        System.out.println("Kode Buku | Nama Buku | NIS Peminjam | Tanggal Pinjam");
        boolean ditemukan = false;

        for (String t : transaksi) {
            String[] info = t.split(",");
            if (info.length == 6 && info[5].equals("0")) {
                ditemukan = true;
                String namaBuku = getDetail(buku, info[2]); 
                System.out.println(info[2] + " | " + namaBuku + " | " + info[1] + " | " + info[3]);
            }
        }
        
        if (!ditemukan) {
            System.out.println("Tidak ada data. Semua buku saat ini telah dikembalikan.");
        }
    }

    public static void lewatJatuhTempo() {
        List<String> transaksi = FileHandling.readFile(namaFileTransaksi);
        List<String> siswa = FileHandling.readFile(ManagerSiswa.namaFile);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate hariIni = LocalDate.now();

        System.out.println("\n=== Laporan Peminjam Melebihi Jatuh Tempo ===");
        System.out.println("NIS | Nama Siswa | Kode Buku | Tanggal Jatuh Tempo");
        boolean ditemukan = false;

        for (String t : transaksi) {
            String[] info = t.split(",");
            if (info.length == 6 && info[5].equals("0")) {
                try {
                    LocalDate jatuhTempo = LocalDate.parse(info[4], formatter);

                    if (hariIni.isAfter(jatuhTempo)) {
                        ditemukan = true;
                        String namaSiswa = getDetail(siswa, info[1]); 
                        System.out.println(info[1] + " | " + namaSiswa + " | " + info[2] + " | " + info[4]);
                    }
                } catch (Exception e) {
                    System.out.println("Format tanggal salah pada kode transaksi: " + info[0]);
                }
            }
        }
        
        if (!ditemukan) {
            System.out.println("Aman! Tidak ada peminjaman yang melewati tanggal jatuh tempo.");
        }
    }

    private static String getDetail(List<String> listData, String idTarget) {
        for (String data : listData) {
            String[] info = data.split(",");
            if (info.length >= 2 && info[0].equals(idTarget)) {
                return info[1];
            }
        }
        return "Data tidak ditemukan";
    }
}