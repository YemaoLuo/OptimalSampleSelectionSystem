import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBHelperUI {

    public boolean save(String fileName, String data) {
        if (loadDB()) {
            String folderPath = "./db/";
            int count = 1;
            File file = new File(folderPath + fileName + "_" + count + ".data");
            while (file.exists()) {
                count++;
                file = new File(folderPath + fileName + "_" + count + ".data");
            }
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(data);
                bw.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public String load(String fileName) {
        StringBuilder data = null;
        if (loadDB()) {
            String filePath = "./db/";
            try {
                File file = new File(filePath + fileName);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    data = (data == null ? new StringBuilder("null") : data).append(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data == null ? null : data.toString();
    }

    public boolean remove(String fileName) {
        try {
            String filePath = "./db/";
            File file = new File(filePath + fileName);
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loadDB() {
        File folder = new File("./db");
        if (!folder.exists()) {
            return folder.mkdirs();
        } else {
            return true;
        }
    }

    public List<String> readAllDBFiles() {
        List<String> data = new ArrayList<>();
        if (loadDB()) {
            File folder = new File("./db");
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File folder, String name) {
                    return name.toLowerCase().endsWith(".data");
                }
            };
            File[] files = folder.listFiles(filter);
            assert files != null;
            data = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
        }
        return data;
    }

    public static void main(String[] args) {
        DBHelperUI dbh = new DBHelperUI();

        System.out.println("RADF");
        List<String> allFiles = dbh.readAllDBFiles();
        System.out.println(allFiles);

        System.out.println("SAVE");
        System.out.println(dbh.save("test", "test\n  test"));

        System.out.println("LOAD");
        allFiles = dbh.readAllDBFiles();
        System.out.println(allFiles);
        for (String allFile : allFiles) {
            System.out.println(dbh.load(allFile));
        }

        System.out.println("REMOVE");
        for (String allFile : allFiles) {
            System.out.println(dbh.remove(allFile));
        }
    }
}
