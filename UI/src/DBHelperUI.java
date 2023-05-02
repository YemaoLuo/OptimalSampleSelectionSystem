import java.io.*;
import java.util.*;
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
                FileInputStream fis = new FileInputStream("./db/cache.db");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String, Date> cache = (HashMap<String, Date>) ois.readObject();
                cache.put(fileName + "-" + count + ".data", new Date());
                FileOutputStream fos = new FileOutputStream("./db/cache.db");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cache);
                oos.close();
                fos.close();
            } catch (Exception e) {
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
                File file = new File(filePath + fileName + ".data");
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
            File file = new File(filePath + fileName + ".data");
            file.delete();
            FileInputStream fis = new FileInputStream("./db/cache.db");
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String, Date> cache = (HashMap<String, Date>) ois.readObject();
            cache.remove(fileName);
            FileOutputStream fos = new FileOutputStream("./db/cache.db");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cache);
            oos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean loadDB() {
        try {
            File folder = new File("./db");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File("./db/cache.db");
            if (!file.exists()) {
                HashMap<String, Date> storage = new HashMap<>();
                FileOutputStream fos = new FileOutputStream("./db/cache.db");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(storage);
                oos.close();
                fos.close();
            }
            return true;
        } catch (Exception e) {
            return false;
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
            try {
                FileInputStream fis = new FileInputStream("./db/cache.db");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String, Date> cache = (HashMap<String, Date>) ois.readObject();
                data = Arrays.stream(files).map(File::getName).collect(Collectors.toList());
                data = data.stream().sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return cache.getOrDefault(o2, new Date()).compareTo(cache.getOrDefault(o1, new Date()));
                    }
                }).map((file -> file.substring(0, file.lastIndexOf('.')))).collect(Collectors.toList());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                data = Arrays.stream(files).map(File::getName).sorted(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        o1 = o1.replace("-", "");
                        o2 = o2.replace("-", "");
                        return Integer.parseInt(o2.substring(0, o2.lastIndexOf('.'))) -
                                Integer.parseInt(o1.substring(0, o1.lastIndexOf('.')));
                    }
                }).collect(Collectors.toList());
            }
        }
        return data;
    }

    public void removeAll() {
        List<String> files = readAllDBFiles();
        for (String file : files) {
            remove(file + ".data");
        }
        File cache = new File("./db/cache.db");
        cache.delete();
    }
}