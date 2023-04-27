package algorithm;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SolutionHelper1_0 {

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
    public List<List<Integer>> generateCoverList(List<Integer> chosenSamples, int j) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        generateCoverListCombinations(chosenSamples, j, 0, temp, result);
        return result;
    }

    private static void generateCoverListCombinations(List<Integer> chosenSamples, int k, int start,
                                                      List<Integer> temp, List<List<Integer>> result) {
        if (temp.size() == k) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < chosenSamples.size(); i++) {
            temp.add(chosenSamples.get(i));
            generateCoverListCombinations(chosenSamples, k, i + 1, temp, result);
            temp.remove(temp.size() - 1);
        }
    }

    public Map<List<Integer>, List<List<Integer>>> generateCoverListMap(List<List<Integer>> coverList, int s) {
        Map<List<Integer>, List<List<Integer>>> coverListMap = new HashMap<>();
        for (List<Integer> integers : coverList) {
            coverListMap.put(integers, generateCoverList(integers, s));
        }
        return coverListMap;
    }


    // Generate results using hill-climbing method
    public void removeCoverListMapKey(List<Integer> candidateResult, Map<List<Integer>, List<List<Integer>>> coverListMap) {
        coverListMap.remove(candidateResult);
        Iterator<Map.Entry<List<Integer>, List<List<Integer>>>> iterator = coverListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<List<Integer>, List<List<Integer>>> next = iterator.next();
            List<List<Integer>> coverList = next.getValue();
            for (List<Integer> integerList : coverList) {
                if (candidateResult.containsAll(integerList)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    public List<Integer> getCandidateResult(List<List<Integer>> possibleResults, Map<List<Integer>, List<List<Integer>>> coverListMap) {
        AtomicInteger maxCover = new AtomicInteger(Integer.MIN_VALUE);
        AtomicInteger index = new AtomicInteger();
        for (int i = 0; i < possibleResults.size(); i++) {
            final int indexI = i;
            List<Integer> possibleResult = possibleResults.get(i);
            AtomicInteger tempMax = new AtomicInteger();
            coverListMap.entrySet().parallelStream().forEach(next -> {
                List<List<Integer>> coverList = next.getValue();
                for (List<Integer> integerList : coverList) {
                    if (possibleResult.containsAll(integerList)) {
                        tempMax.getAndIncrement();
                        break;
                    }
                }
                if (tempMax.get() > maxCover.get()) {
                    maxCover.set(tempMax.get());
                    index.set(indexI);
                }
            });
        }
        return possibleResults.get(index.get());
    }

    public List<List<Integer>> getResult(List<List<Integer>> possibleResults, Map<List<Integer>, List<List<Integer>>> coverListMap) {
        List<List<Integer>> result = new ArrayList<>();
        double initSize = coverListMap.size();
        long removeCoverListMapKeyTime = 0, getCandidateResultTime = 0;
        while (!coverListMap.isEmpty()) {
            long startTime = System.currentTimeMillis();
            System.out.println(String.format("%.2f", (1 - coverListMap.size() / initSize) * 100) + "%");
            List<Integer> candidateResult = getCandidateResult(possibleResults, coverListMap);
            getCandidateResultTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            result.add(candidateResult);
            removeCoverListMapKey(candidateResult, coverListMap);
            possibleResults.remove(candidateResult);
            removeCoverListMapKeyTime += System.currentTimeMillis() - startTime;
        }
        System.out.println("Remove cover list time: " + removeCoverListMapKeyTime + " ms");
        System.out.println("Get candidate result time: " + getCandidateResultTime + " ms");
        System.out.println("=========================================");
        return result;
    }
}