import java.util.List;
import java.util.Scanner;

public class SistemPerpus {
    private static Scanner input = new Scanner(System.in);
    private static boolean login = false;
    private static String nama = "";

    private static void login(){
        System.out.println("===LOGIN===");
        System.out.print("Masukkan NIP :");
        String NIPlogin = input.nextLine();
         List <String> staff = FileHandling.readFile("staff.txt");
         for (String s : staff){
             String [] info = s.split(",");
             if (info.length >= 2 && info[0].equals(NIPlogin)){
                 login = true;
                 nama = info[1];
                 System.out.println("Login berhasil! Selamat Datang " + nama);
                 return;
             }
         }
         System.out.println("NIP tidak ditemukan");
    }

    private static void menu(){
        while(login) {
            System.out.println("===MENU===");
            System.out.println("1. Managemen Siswa");
            System.out.println("2. Managemen Buku");
            System.out.println("3. Managemen Staff");
            System.out.println("4. Peminjaman");
            System.out.println("5. Pengembalian");
            System.out.println("6. Laporan");
            System.out.println("7. Keluar");
            System.out.print("Pilihan: ");
            String pilihan = input.nextLine();

            switch (pilihan) {
                case "1":
                    System.out.println("1. Daftar Siswa");
                    System.out.println("2. Tambah Siswa");
                    System.out.println("3. Perbarui Siswa");
                    System.out.println("4. Hapus Siswa");
                    System.out.println("Pilihan: ");
                    String pilihanSiswa = input.nextLine();

                    switch (pilihanSiswa) {
                        case "1":
                            ManagerSiswa.viewSiswa();
                            break;
                        case "2":
                            ManagerSiswa.addSiswa();
                            break;
                        case "3":
                            ManagerSiswa.updateSiswa();
                            break;
                        case "4":
                            ManagerSiswa.deleteSiswa();
                            break;
                        default:
                            System.out.println("Pilihan invalid");
                    }
                    break;
                case "2":
                    System.out.println("1. Daftar Buku");
                    System.out.println("2. Tambah Buku");
                    System.out.println("3. Perbarui Buku");
                    System.out.println("4. Hapus Buku");
                    System.out.println("Pilihan: ");
                    String pilihanBuku = input.nextLine();

                    switch (pilihanBuku) {
                        case "1":
                            ManagerBuku.viewBuku();
                            break;
                        case "2":
                            ManagerBuku.addBuku();
                            break;
                        case "3":
                            ManagerBuku.updateBuku();
                            break;
                        case "4":
                            ManagerBuku.deleteBuku();
                            break;
                        default:
                            System.out.println("Pilihan invalid");
                    }
                    break;
                case "3":
                    System.out.println("\n--- Menu Pegawai ---");
                    System.out.println("1. Daftar Pegawai");
                    System.out.println("2. Tambah Pegawai");
                    System.out.println("3. Perbarui Pegawai");
                    System.out.println("4. Hapus Pegawai");
                    System.out.print("Pilihan: ");

                    String pilihanPegawai = input.nextLine();

                    switch (pilihanPegawai) {
                        case "1":
                            ManagerStaff.viewStaff();
                            break;
                        case "2":
                            ManagerStaff.addStaff();
                            break;
                        case "3":
                            ManagerStaff.updateStaff();
                            break;
                        case "4":
                            ManagerStaff.deleteStaff();
                            break;
                        default:
                            System.out.println("Pilihan invalid");
                    }
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    System.out.println("Keluar...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan invalid");
            }
        }
    }

    public static void main(String[] args) {
        if (!login) {
            login();
        }
        menu();
    }
}
