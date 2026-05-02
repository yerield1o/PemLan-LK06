import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    public static List<String> readFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "File " + fileName + " tidak ditemukan",
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi error saat membaca file " + fileName,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public static void writeFile(String fileName, String isi, boolean lanjutan) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, lanjutan))) {
            bw.write(isi);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi error saat menulis file " + fileName,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void rewriteFile(String fileName, List<String> isi) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            for (String line : isi) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Terjadi error saat menulis ulang file " + fileName,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}