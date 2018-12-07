package day7;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Puzzle {

    public static final boolean IS_TEST = false;
    public static final String fileName = IS_TEST ? "example.txt" : "input.txt";
    public static final String INPUT_FILE = System.getProperty("user.dir") + "/out/production/advent2018/day7/" + fileName;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int NUM_WORKERS = IS_TEST ? 2 : 5;
    public static final int MIN_SECONDS = IS_TEST ? 0 : 60;

    public static void main(String[] args) {
        List<String> input = new ArrayList<>();

        try {
            input = Files.readAllLines(new File(INPUT_FILE).toPath());
        } catch (Exception e) {
            System.out.println("Oh shit! " + e);
        }

        Map<String, List<String>> stepsAndSuccessors = getStepsAndSuccessors(input);

        /********************************
         * Part 1
         ********************************/
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

        System.out.println();
        /********************************
         * Part 2
         ********************************/
        stepsAndSuccessors = getStepsAndSuccessors(input);

        result = "";
        int seconds = 0;
        Map<String, Integer> workers = new TreeMap<>();
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers.put(Integer.toString(i), 0);
        }

        printHeaders();

        while (!stepsAndSuccessors.isEmpty()) {
            Set<String> currentSteps = new HashSet<>(workers.keySet());
            List<String> finishedWorkers = new ArrayList<>();
            Map<String, Integer> newWorkers = new HashMap<>();

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(workers.entrySet());
            entries.sort(Map.Entry.comparingByValue());

            for (int i = entries.size() - 1; i >= 0; i--) {
                Map.Entry<String, Integer> worker = entries.get(i);
                if (worker.getValue() == 0) {
                    finishedWorkers.add(worker.getKey());
                    currentSteps.remove(worker.getKey());
                    stepsAndSuccessors.remove(worker.getKey());
                    String nextStep = getNextStep(stepsAndSuccessors, currentSteps);
                    if (nextStep != null) {
                        newWorkers.put(nextStep, ALPHABET.indexOf(nextStep) + MIN_SECONDS);
                        currentSteps.add(nextStep);
                    }
                } else {
                    int current = worker.getValue();
                    worker.setValue(current - 1);
                }
            }

            for (String worker : finishedWorkers) {
                workers.remove(worker);
                if (worker.matches("[A-Z]")) {
                    result += worker;
                }
            }
            workers.putAll(newWorkers);
            for (int i = workers.size(); i < NUM_WORKERS; i++) {
                workers.put(Integer.toString(i), 0);
            }

            System.out.print(seconds + "\t\t");
            for (String worker : workers.keySet()) {
                System.out.print(worker + "\t\t\t");
            }
            System.out.println(result);

            seconds++;
        }

        seconds--;
        System.out.println("Result is " + result);
        System.out.println("Time is " + seconds); //1072

        if (IS_TEST) {
            System.out.println(result.equals("CABFDE"));
            System.out.println(seconds == 15);
        }
    }

    private static void printHeaders() {
        System.out.print("Second\t");
        for (int i = 0; i < NUM_WORKERS; i++) {
            System.out.print("Worker " + (i + 1) + "\t");
        }
        System.out.println("Done");
    }

    private static Map<String, List<String>> getStepsAndSuccessors(List<String> input) {
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
        return stepsAndSuccessors;
    }

    private static String getNextStep(Map<String, List<String>> stepsAndSuccessors) {
        List<String> allSuccessors = stepsAndSuccessors.values().stream().flatMap(l -> l.stream()).distinct().collect(Collectors.toList());
        List<String> allPredecessors = new ArrayList(stepsAndSuccessors.keySet());

        allPredecessors.removeAll(allSuccessors);
        Collections.sort(allPredecessors);

        return allPredecessors.get(0);
    }

    private static String getNextStep(Map<String, List<String>> stepsAndSuccessors, Collection<String> inProgress) {
        List<String> allSuccessors = stepsAndSuccessors.values().stream()
                .flatMap(l -> l.stream())
                .distinct()
                .collect(Collectors.toList());
        List<String> allPredecessors = new ArrayList(stepsAndSuccessors.keySet());

        allPredecessors.removeAll(allSuccessors);
        allPredecessors.removeAll(inProgress);
        Collections.sort(allPredecessors);

        if (!allPredecessors.isEmpty()) {
            return allPredecessors.get(0);
        } else {
            return null;
        }
    }

}
