import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Transaksi {
    public static String namaFile = "transaksi.txt";
    private static Scanner scan = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void pinjamBuku() {
        try {
            System.out.println("=== PEMINJAMAN BUKU ===");

            System.out.print("Kode Transaksi: ");
            String kode = scan.nextLine();

            System.out.print("NIS: ");
            String nis = scan.nextLine();

            System.out.print("Kode Buku: ");
            String kodeBuku = scan.nextLine();

            int jumlah = hitungPinjaman(nis);
            if (jumlah >= 2) {
                System.out.println("Siswa sudah meminjam maksimal 2 buku!");
                return;
            }

            String tglPinjam = LocalDate.now().format(formatter);
            String tglKembali = LocalDate.now().plusDays(7).format(formatter);

            String status = "0";

            String data = kode + "," + nis + "," + kodeBuku + "," +
                          tglPinjam + "," + tglKembali + "," + status;

            FileHandling.writeFile(namaFile, data, true);

            System.out.println("Peminjaman berhasil!");

        } catch (Exception e) {
            System.out.println("Terjadi error saat peminjaman!");
        }
    }

    public static void kembalikanBuku() {
        try {
            System.out.println("=== PENGEMBALIAN BUKU ===");
            System.out.print("Masukkan Kode Transaksi: ");
            String kode = scan.nextLine();

            List<String> transaksi = FileHandling.readFile(namaFile);
            boolean ditemukan = false;

            for (int i = 0; i < transaksi.size(); i++) {
                String[] info = transaksi.get(i).split(",");

                if (info.length == 6 && info[0].equals(kode)) {
                    if (info[5].equals("1")) {
                        System.out.println("Buku sudah dikembalikan sebelumnya!");
                        return;
                    }

                    info[5] = "1"; // ubah status
                    transaksi.set(i, String.join(",", info));
                    ditemukan = true;
                    break;
                }
            }

            if (ditemukan) {
                FileHandling.rewriteFile(namaFile, transaksi);
                System.out.println("Buku berhasil dikembalikan!");
            } else {
                System.out.println("Kode transaksi tidak ditemukan!");
            }

        } catch (Exception e) {
            System.out.println("Terjadi error saat pengembalian!");
        }
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
}
