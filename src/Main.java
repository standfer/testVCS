import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: grinenko
 * Created: 06.03.2020 16:04
 */
public class Main {
    public static final String PATH = "contacts\\";
    public static final String ALL_CONTACTS_PATH = "2021_grinenko_contacts.vcf";

    public static void main(String[] args) throws IOException {
        FileReader fr = null;
        try {
            fr = new FileReader(ALL_CONTACTS_PATH);
            BufferedReader reader = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            String fileName = "";

            while (line != null) {
                sb.append(line).append("\n");
                if (line.contains("FN:")) {
                    fileName = line.substring(3);
                } else if ("END:VCARD".equals(line)) {
                    writeUsingFileWriter(sb.toString(), fileName);
                    sb = new StringBuilder();
                }

                line = reader.readLine();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            fr.close();
        }
    }

    private static void writeUsingFileWriter(String data, String fileName) {
        if (fileName.isEmpty()) fileName = getRandomFileName();
        File file = new File(PATH + fileName + ".vcf");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeUsingOutputStream(String data, String fileName) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("d:\\Projects\\Work\\testVCS\\src\\" + fileName + ".vcf"));
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getRandomFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
