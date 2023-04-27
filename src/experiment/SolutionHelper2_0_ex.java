package experiment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SolutionHelper2_0_ex {

    // Validate input
    public boolean validate(int m, int n, int k, int j, int s) {
        if (m < 45 || m > 54) {
            return false;
        }
        if (n < 7 || n > 25) {
            return false;
        }
        if (k < 4 || k > 7) {
            return false;
        }
        if (s < 3 || s > 7) {
            return false;
        }
        return j >= Math.min(s, k) && j <= Math.max(s, k);
    }

    // Generate chosen samples
    public List<Integer> generateChosenSamples(int m, int n) {
        List<Integer> chosenSamples = new ArrayList<>();
        Random random = new Random();
        while (chosenSamples.size() < n) {
            int candidateNum = random.nextInt(m) + 1;
            if (chosenSamples.contains(candidateNum)) {
                continue;
            }
            chosenSamples.add(candidateNum);
        }
        return chosenSamples;
    }


    // Generate all possible combinations of results
    public List<List<Integer>> generatePossibleResults(List<Integer> chosenSamples, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        generateResultsCombinations(chosenSamples, k, 0, temp, result);
        return result;
    }

    private static void generateResultsCombinations(List<Integer> chosenSamples, int k, int start,
                                                    List<Integer> temp, List<List<Integer>> result) {
        if (temp.size() == k) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < chosenSamples.size(); i++) {
            temp.add(chosenSamples.get(i));
            generateResultsCombinations(chosenSamples, k, i + 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }


    // Generate all results need to be covered
    public List<Set<Integer>> generateCoverList(List<Integer> chosenSamples, int j) {
        List<Set<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        generateCoverListCombinations(chosenSamples, j, 0, temp, result);
        return result;
    }

    private static void generateCoverListCombinations(List<Integer> chosenSamples, int k, int start,
                                                      List<Integer> temp, List<Set<Integer>> result) {
        if (temp.size() == k) {
            result.add(new HashSet<>(temp));
            return;
        }
        for (int i = start; i < chosenSamples.size(); i++) {
            temp.add(chosenSamples.get(i));
            generateCoverListCombinations(chosenSamples, k, i + 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }


    // Generate results using hill-climbing method
    public List<Integer> getCandidateResult(List<List<Integer>> possibleResults, List<Set<Integer>> coverList, int s) {
        AtomicInteger max = new AtomicInteger(0);
        AtomicReference<List<Integer>> res = new AtomicReference<>();
        possibleResults.parallelStream().unordered().forEach((possibleResult -> {
            AtomicInteger tempMax = new AtomicInteger(0);
            coverList.parallelStream().unordered().forEach(coverSet -> {
                int count = 0;
                for (Integer integer : possibleResult) {
                    if (coverSet.contains(integer)) {
                        count++;
                    }
                }
                if (count >= s) {
                    tempMax.getAndIncrement();
                }
            });
            if (tempMax.get() > max.get()) {
                max.set(tempMax.get());
                res.set(possibleResult);
            }
        }));
        return res.get();
    }

    public List<Integer> getCandidateResultSingleThread(List<List<Integer>> possibleResults, List<Set<Integer>> coverList, int s) {
        AtomicInteger max = new AtomicInteger(0);
        AtomicReference<List<Integer>> res = new AtomicReference<>();
        possibleResults.forEach((possibleResult -> {
            AtomicInteger tempMax = new AtomicInteger(0);
            coverList.forEach(coverSet -> {
                int count = 0;
                for (Integer integer : possibleResult) {
                    if (coverSet.contains(integer)) {
                        count++;
                    }
                }
                if (count >= s) {
                    tempMax.getAndIncrement();
                }
            });
            if (tempMax.get() > max.get()) {
                max.set(tempMax.get());
                res.set(possibleResult);
            }
        }));
        return res.get();
    }

    public void removeCoveredResults(List<Integer> candidateResult, List<Set<Integer>> coverList, int s) {
        Iterator<Set<Integer>> iterator = coverList.iterator();
        while (iterator.hasNext()) {
            Set<Integer> coverSet = iterator.next();
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

    public List<List<Integer>> getResult(List<List<Integer>> possibleResults, List<Set<Integer>> coverList, int s) {
        List<List<Integer>> result = new ArrayList<>();
        double initSize = coverList.size();
        while (coverList.size() != 0) {
            System.out.println(String.format("%.2f", (1 - coverList.size() / initSize) * 100) + "%");
            List<Integer> candidateResult = getCandidateResult(possibleResults, coverList, s);
            removeCoveredResults(candidateResult, coverList, s);
            result.add(candidateResult);
        }
        return result;
    }

    public List<List<Integer>> getResultSingleThread(List<List<Integer>> possibleResults, List<Set<Integer>> coverList, int s) {
        List<List<Integer>> result = new ArrayList<>();
        double initSize = coverList.size();
        while (coverList.size() != 0) {
            System.out.println(String.format("%.2f", (1 - coverList.size() / initSize) * 100) + "%");
            List<Integer> candidateResult = getCandidateResultSingleThread(possibleResults, coverList, s);
            removeCoveredResults(candidateResult, coverList, s);
            result.add(candidateResult);
        }
        return result;
    }
}