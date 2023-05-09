package algorithm;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ValidateAnswers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(), n = sc.nextInt(), k = sc.nextInt(), j = sc.nextInt(), s = sc.nextInt();
        SolutionHelper2_2 sh = new SolutionHelper2_2();
        List<Integer> chosenSamples = sh.generateChosenSamples(m, n);
        List<List<Integer>> possibleResults = sh.generatePossibleResults(chosenSamples, k);
        List<List<Integer>> coverList = sh.generateCoverList(chosenSamples, j);
        List<List<Integer>> result = sh.getResult(possibleResults, coverList, s);

        System.out.println("Remain cover list: " + validate(result, coverList, s));
    }

    public static int validate(List<List<Integer>> result, List<List<Integer>> coverList, int s) {
        for (List<Integer> integers : result) {
            removeCoveredResults(integers, coverList, s);
        }
        return coverList.size();
    }

    public static void removeCoveredResults(List<Integer> candidateResult, List<List<Integer>> coverList, int s) {
        Iterator<List<Integer>> iterator = coverList.iterator();
        while (iterator.hasNext()) {
            List<Integer> coverSet = iterator.next();
            int count = 0;
            for (Integer integer : candidateResult) {
                if (coverSet.contains(integer)) {
                    count++;
                }
            }
            if (count >= s) {
                iterator.remove();
            }
        }
    }
}
