package algorithm;

import java.util.List;
import java.util.Scanner;

public class OptimizeSolution2_2 {

    public static void optimizeSolution() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input m, n, k, j, s: ");
        SolutionHelper2_2 sh = new SolutionHelper2_2();
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
        System.out.println("Cover list : " + coverList);
        System.out.println("Cover list size: " + coverList.size());
        System.out.println("Time cost: " + (System.currentTimeMillis() - tempTime) + " ms");
        System.out.println("=====================================");

        tempTime = System.currentTimeMillis();
        List<List<Integer>> result = sh.getResult(possibleResults, coverList, s);
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
