package experiment;

import algorithm.SolutionHelper2_2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentOSWithTCInMoodle {

    public static void main(String[] args) {
        SolutionHelper2_2 sh = new SolutionHelper2_2();

        String filePath = "/os22_tc.log";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            List<int[]> tc = new ArrayList<>();
            tc.add(new int[]{45, 10, 6, 5, 5, 50});
            tc.add(new int[]{45, 12, 6, 5, 5, 132});
            tc.add(new int[]{45, 13, 6, 5, 5, 245});
            tc.add(new int[]{45, 10, 6, 4, 4, 37});
            tc.add(new int[]{45, 12, 6, 4, 4, 42});
            tc.add(new int[]{45, 16, 6, 4, 4, 162});
            tc.add(new int[]{45, 11, 6, 6, 5, 26});
            tc.add(new int[]{45, 12, 6, 6, 5, 42});
            tc.add(new int[]{45, 16, 6, 6, 5, 280});
            tc.add(new int[]{45, 12, 6, 5, 4, 18});
            tc.add(new int[]{45, 13, 6, 5, 4, 28});
            tc.add(new int[]{45, 16, 6, 5, 4, 65});
            tc.add(new int[]{45, 12, 6, 6, 4, 6});
            tc.add(new int[]{45, 13, 6, 6, 4, 12});
            tc.add(new int[]{45, 16, 6, 6, 4, 38});
            for (int i = 0; i < 15; i++) {
                int[] ints = tc.get(i);
                int m = ints[0], n = ints[1], k = ints[2], j = ints[3], s = ints[4], or = ints[5];
                System.out.println("ID = " + i);
                bw.write("ID = " + i + "\n");
                bw.write("m = " + m + ", n = " + n + ", k = " + k + ", j = " + j + ", s = " + s + "\n");
                bw.write("====================================\n");

                long startTime = System.currentTimeMillis();

                long tempTime = System.currentTimeMillis();
                List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
                bw.write("Chosen samples: " + chosenSamples + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms\n");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
                bw.write("Possible results size: " + possibleResults.size() + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms" + "\n");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
                bw.write("Cover list size: " + coverList.size() + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms\n");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> result = sh.getResult(possibleResults, coverList, s);
                //List<List<Integer>> result = sh20.getResultSingleThread(possibleResults, coverList, s);
                bw.write("Result: " + result + "\n");
                bw.write("Result size: " + result.size() + "\n");
                bw.write("Difference: " + (result.size() - or) + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms\n");
                bw.write("=====================================\n");

                bw.write("Total time cost: " + (System.currentTimeMillis() - startTime) + " ms\n");

                bw.write("====================================\n");
                bw.newLine();
                bw.newLine();
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
