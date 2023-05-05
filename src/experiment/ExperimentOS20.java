package experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ExperimentOS20 {

    public static void main(String[] args) {
        SolutionHelper2_0_ex sh20 = new SolutionHelper2_0_ex();

        String filePath = "/Users/yemaoluo/Documents/CODING/AIAlgorithm/experiment/os20.log";
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
            int m = 54, k = 7, j = 5, s = 3;
            for (int n = 7; n <= 20; n++) {
                System.out.println("n = " + n);
                bw.write("n = " + n + "\n");
                bw.write("====================================\n");

                long startTime = System.currentTimeMillis();

                long tempTime = System.currentTimeMillis();
                List<Integer> chosenSamples = sh20.generateChosenSamples(m, n);
                bw.write("Chosen samples: " + chosenSamples + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms\n");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> possibleResults = sh20.generatePossibleResults(chosenSamples, k);
                bw.write("Possible results size: " + possibleResults.size() + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms" + "\n");

                tempTime = System.currentTimeMillis();
                List<Set<Integer>> coverList = sh20.generateCoverList(chosenSamples, j);
                bw.write("Cover list size: " + coverList.size() + "\n");
                bw.write("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms\n");

                tempTime = System.currentTimeMillis();
                List<List<Integer>> result = sh20.getResult(possibleResults, coverList, s);
                //List<List<Integer>> result = sh20.getResultSingleThread(possibleResults, coverList, s);
                bw.write("Result: " + result + "\n");
                bw.write("Result size: " + result.size() + "\n");
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
