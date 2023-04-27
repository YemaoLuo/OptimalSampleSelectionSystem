import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DBHelperUI {

    public boolean save(String fileName, String data) {
        if (loadDB()) {
            String folderPath = "./db/";
            int count = 1;
            File file = new File(folderPath + fileName + "-" + count + ".data");
            while (file.exists()) {
                count++;
                file = new File(folderPath + fileName + "-" + count + ".data");
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
        StringBuilder data = new StringBuilder();
        if (loadDB()) {
            String filePath = "./db/";
            try {
                File file = new File(filePath + fileName);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    data.append(line + "\n");
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
            data = Arrays.stream(files).map(File::getName).sorted(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    o1 = o1.replace("-", "");
                    o2 = o2.replace("-", "");
                    return Integer.parseInt(o1.substring(0, o1.lastIndexOf('.'))) -
                            Integer.parseInt(o2.substring(0, o2.lastIndexOf('.')));
                }
            }).collect(Collectors.toList());
        }
        return data;
    }

    public void removeAll() {
        List<String> files = readAllDBFiles();
        for (String file : files) {
            remove(file);
        }
    }
}
