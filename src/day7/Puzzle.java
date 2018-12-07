package day7;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle {

    public static final boolean IS_TEST = false;
    public static final String fileName = IS_TEST ? "example.txt" : "input.txt";
    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day7/" + fileName;

    public static void main(String[] args) {
        List<String> input = new ArrayList<>();

        try {
            input = Files.readAllLines(new File(INPUT_FILE).toPath());
        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }

        Map<String, List<String>> stepsAndSuccessors = new HashMap<>();

        for (String line : input) {
            String step = line.substring(5, 6);
            String successor = line.substring(36, 37);
            List<String> successors = stepsAndSuccessors.getOrDefault(step, new ArrayList<>());
            successors.add(successor);
            stepsAndSuccessors.put(step, successors);

            if (!stepsAndSuccessors.containsKey(successor)) {
                stepsAndSuccessors.put(successor, new ArrayList<>());
            }
        }

        System.out.println(stepsAndSuccessors);

        String result = "";
        while (!stepsAndSuccessors.isEmpty()) {
            String nextStep = getNextStep(stepsAndSuccessors);
            result += nextStep;
            stepsAndSuccessors.remove(nextStep);
        }

        System.out.println("Result is " + result);
        if (IS_TEST) {
            System.out.println(result.equals("CABDFE"));
        }

    }

    private static String getNextStep(Map<String, List<String>> stepsAndSuccessors) {
        List<String> allSuccessors = stepsAndSuccessors.values().stream().flatMap(l -> l.stream()).distinct().collect(Collectors.toList());
        List<String> allPredecessors = new ArrayList(stepsAndSuccessors.keySet());

        allPredecessors.removeAll(allSuccessors);
        Collections.sort(allPredecessors);

        return allPredecessors.get(0);
    }

}
