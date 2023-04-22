import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OptimizeSolution2_0 {

    public static void optimizeSolution() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input m, n, k, j, s: ");
        SolutionHelper2_0 sh = new SolutionHelper2_0();
        int m = sc.nextInt(), n = sc.nextInt(), k = sc.nextInt(), j = sc.nextInt(), s = sc.nextInt();
        long startTime = System.currentTimeMillis();
        System.out.println("=====================================");

        long tempTime = System.currentTimeMillis();
        List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
        System.out.println("Chosen samples: " + chosenSamples);
        System.out.println("Chosen samples size: " + chosenSamples.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        tempTime = System.currentTimeMillis();
        List<List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
        System.out.println("Possible results: " + possibleResults);
        System.out.println("Possible results size: " + possibleResults.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        tempTime = System.currentTimeMillis();
        List<List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
        Map<List<Integer>, List<List<Integer>>> coverListMap = sh.generateCoverListMap(coverList, s);
        System.out.println("Cover list map: " + coverListMap);
        System.out.println("Cover list map size: " + coverListMap.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        tempTime = System.currentTimeMillis();
        List<List<Integer>> result = sh.getResult(possibleResults, coverListMap, j);
        System.out.println("Result: " + result);
        System.out.println("Result size: " + result.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        System.out.println("Total time cost: " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("=====================================");
    }

    public static void main(String[] args) {
        optimizeSolution();
    }
}
