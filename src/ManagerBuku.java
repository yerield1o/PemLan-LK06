import java.util.List;
import java.util.Scanner;

public class ManagerBuku {
    public static String namaFile = "buku.txt";
    private static Scanner scan = new Scanner(System.in);

    public static void addBuku(){
        System.out.print("Masukkan nama buku: ");
        String nama = scan.nextLine();

        System.out.print("Masukkan kode buku: ");
        String kode = scan.nextLine();

        String buku = kode + "," + nama;

        FileHandling.writeFile(namaFile, buku, true);
        System.out.println("buku berhasil ditambahkan");
    }

    public static void viewBuku() {
        List<String> buku = FileHandling.readFile(namaFile);

        if(buku.isEmpty()){
            System.out.println("Tidak ada buku");
            return;
        }

        System.out.println(" Kode Buku | Nama Buku ");

        for (String k : buku) {
            String[]info = k.split(",");
            if (info.length == 2){
                System.out.println(info[0] + " | " + info[1]);
            }
        }
    }

    public static void updateBuku(){
        System.out.print("Masukkan kode buku: ");
        String kode =  scan.nextLine();

        List <String> buku = FileHandling.readFile(namaFile);
        boolean ditemukan = false;
        for (int i = 0; i < buku.size(); i++){
            String[] info = buku.get(i).split(",");
            if (info.length == 2 && info[0].equals(kode)){
                ditemukan = true;
                System.out.println("Nama buku baru : ");
                String namaBukuBaru = scan.nextLine();

                String bukuBaru = kode + "," + namaBukuBaru;
                buku.set(i,bukuBaru);
                break;
            }
        }

        if (ditemukan){
            FileHandling.rewriteFile(namaFile, buku);
            System.out.println("Buku berhasil diperbaharui");
        }
        else{
            System.out.println("Buku dengan kode " + kode + " tidak ditemukan");
        }
    }

    public static void deleteBuku(){
        System.out.print("Masukkan kode buku: ");
        String kode =  scan.nextLine();

        List <String> buku = FileHandling.readFile(namaFile);
        boolean ditemukan = false;
        for (int i = 0; i < buku.size(); i++) {
            String[] info = buku.get(i).split(",");
            if (info.length == 2 && info[0].equals(kode)) {
                ditemukan = true;
                buku.remove(i);
                break;
            }
        }
        if (ditemukan){
            FileHandling.rewriteFile(namaFile, buku);
            System.out.println("Buku berhasil dihapus!");
        }
        else{
            System.out.println("Buku dengan kode " + kode + " tidak ditemukan");
        }
    }
}
