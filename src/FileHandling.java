import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = br.readLine())!= null){
                list.add(line);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " tidak ditemukan");
        }
        catch (IOException e) {
            System.out.println("Terjadi error saat membaca file " + fileName);
        }
        return list;
    }

    public static void writeFile(String fileName, String isi, boolean lanjutan){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, lanjutan))){
            bw.write(isi);
            bw.newLine();;
        }
        catch (IOException e) {
            System.out.println("Terjadi error saat menulis file " + fileName);
        }
    }

    public static void rewriteFile(String fileName, List<String> isi){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))){
            for (String line : isi){
                bw.write(line);
                bw.newLine();
            }
        }
        catch (IOException e) {
            System.out.println("Terjadi error saat menulis ulang file " + fileName);
        }
    }
}
