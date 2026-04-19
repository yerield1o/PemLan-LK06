import java.util.List;
import java.util.Scanner;

public class ManagerStaff {
    public static String namaFile = "staff.txt";
    private static Scanner scan = new Scanner(System.in);

    public static void addStaff() {
        System.out.print("Masukkan NIP staff: ");
        String nip = scan.nextLine();

        System.out.print("Masukkan nama staff: ");
        String nama = scan.nextLine();

        System.out.print("Masukkan tanggal lahir staff (DD-MM-YYYY): ");
        String tglLahir = scan.nextLine();

        String staff = nip + "," + nama + "," + tglLahir;

        FileHandling.writeFile(namaFile, staff, true);
        System.out.println("Pegawai berhasil ditambahkan");
    }

    public static void viewStaff() {
        List<String> staff = FileHandling.readFile(namaFile);

        if(staff.isEmpty()){
            System.out.println("Tidak ada staff");
            return;
        }

        System.out.println(" NIP Staff | Nama Staff | Tanggal Lahir ");

        for (String k : staff) {
            String[] info = k.split(",");
            if (info.length == 3){
                System.out.println(info[0] + " | " + info[1] + " | " + info[2]);
            }
        }
    }

    public static void updateStaff() {
        System.out.print("Masukkan NIP staff: ");
        String nip = scan.nextLine();

        List<String> staff = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        for (int i = 0; i < staff.size(); i++) {
            String namaBaru = "";
            String tglLahirBaru = "";
            String[] info = staff.get(i).split(",");

            if (info.length == 3 && info[0].equals(nip)) {
                namaBaru = info[1];
                tglLahirBaru = info[2];
                ditemukan = true;
                System.out.println("1. Ubah nama");
                System.out.println("2. Ubah tanggal lahir");
                System.out.print("Pilihan: ");

                String pilihan = scan.nextLine();
                switch (pilihan) {
                    case "1":
                        System.out.print("Nama staff baru: ");
                        namaBaru = scan.nextLine();
                        break;
                    case "2":
                        System.out.print("Tanggal lahir baru (DD-MM-YYYY): ");
                        tglLahirBaru = scan.nextLine();
                        break;
                    default:
                        System.out.println("Pilihan Invalid. Data tidak diubah.");
                        break;
                }

                String staffBaru = nip + "," + namaBaru + "," + tglLahirBaru;
                staff.set(i, staffBaru);
                break;
            }
        }

        if (ditemukan){
            FileHandling.rewriteFile(namaFile, staff);
            System.out.println("Data staff berhasil diperbaharui");
        } else {
            System.out.println("Staff dengan NIP " + nip + " tidak ditemukan");
        }
    }

    public static void deleteStaff() {
        System.out.print("Masukkan NIP staff: ");
        String nip =  scan.nextLine();

        List<String> staff = FileHandling.readFile(namaFile);
        boolean ditemukan = false;

        // Using the safe backward loop for deletion
        for (int i = staff.size() - 1; i >= 0; i--) {
            String[] info = staff.get(i).split(",");
            if (info.length == 3 && info[0].equals(nip)) {
                ditemukan = true;
                staff.remove(i);
                break;
            }
        }

        if (ditemukan){
            FileHandling.rewriteFile(namaFile, staff);
            System.out.println("Staff berhasil dihapus!");
        } else {
            System.out.println("Staff dengan NIP " + nip + " tidak ditemukan");
        }
    }
}
