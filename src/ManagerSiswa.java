import java.util.List;
import java.util.Scanner;

public class ManagerSiswa {
    public static String namaFile = "siswa.txt";
    private static Scanner scan = new Scanner(System.in);

    public static void addSiswa(){
        System.out.print("Masukkan NIS siswa: ");
        String nis = scan.nextLine();

        System.out.print("Masukkan nama siswa: ");
        String nama = scan.nextLine();

        System.out.print("Masukkan alamat siswa: ");
        String alamat = scan.nextLine();

        String siswa = nis + "," + nama + "," + alamat;

        FileHandling.writeFile(namaFile, siswa, true);
        System.out.println("Siswa berhasil ditambahkan");
    }

    public static void viewSiswa() {
        List<String> siswa = FileHandling.readFile(namaFile);

        if(siswa.isEmpty()){
            System.out.println("Tidak ada siswa");
            return;
        }

        System.out.println(" NIS Siswa | Nama Siswa | Alamat Siswa ");

        for (String k : siswa) {
            String[]info = k.split(",");
            if (info.length == 3){
                System.out.println(info[0] + " | " + info[1] + " | " + info[2]);
            }
        }
    }

    public static void updateSiswa() {
        System.out.print("Masukkan NIS siswa: ");
        String nis = scan.nextLine();

        List<String> siswa = FileHandling.readFile(namaFile);
        boolean ditemukan = false;
        for (int i = 0; i < siswa.size(); i++) {
            String namaBaru = "";
            String alamatBaru = "";
            String[] info = siswa.get(i).split(",");
            if (info.length == 3 && info[0].equals(nis)) {
                namaBaru = info[1];
                alamatBaru = info[2];
                ditemukan = true;
                System.out.println("1. Ubah nama");
                System.out.println("2. Ubah alamat");
                System.out.println("Pilihan: ");
                String pilihan = scan.nextLine();
                switch (pilihan) {
                    case "1":
                        System.out.println("Nama siswa baru: ");
                        namaBaru = scan.nextLine();
                        break;
                    case "2":
                        System.out.println("Alamat Siswa Baru: ");
                        alamatBaru = scan.nextLine();
                        break;
                    default:
                        System.out.println("Pilihan Invalid");
                        break;
                }
                String siswaBaru = nis + "," + namaBaru + "," + alamatBaru;
                siswa.set(i, siswaBaru);
                break;
            }
        }
        if (ditemukan){
            FileHandling.rewriteFile(namaFile, siswa);
            System.out.println("Siswa berhasil diperbaharui");
        }
        else{
            System.out.println("Siswa dengan NIS " + nis + " tidak ditemukan");
        }
    }

    public static void deleteSiswa(){
        System.out.print("Masukkan NIS siswa: ");
        String nis =  scan.nextLine();

        List <String> siswa = FileHandling.readFile(namaFile);
        boolean ditemukan = false;
        for (int i = siswa.size() - 1; i >= 0; i--) {
            String[] info = siswa.get(i).split(",");
            if (info.length == 3 && info[0].equals(nis)) {
                ditemukan = true;
                siswa.remove(i);
                break;
            }
        }
        if (ditemukan){
            FileHandling.rewriteFile(namaFile, siswa);
            System.out.println("Siswa berhasil dihapus!");
        }
        else{
            System.out.println("Siswa dengan NIS " + nis + " tidak ditemukan");
        }
    }
}
