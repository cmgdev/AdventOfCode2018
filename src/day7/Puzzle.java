package day7;

import base.AbstractPuzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle extends AbstractPuzzle {

    public static final boolean IS_TEST = false;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int NUM_WORKERS = IS_TEST ? 2 : 5;
    public static final int MIN_SECONDS = IS_TEST ? 0 : 60;

    public Puzzle() {
        super(IS_TEST);
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
        List<String> input = puzzle.readFile(7);

        StateMachine stateMachine = new StateMachine(MIN_SECONDS);
        stateMachine.build(input);

        /********************************
         * Part 1
         ********************************/
        String result = "";
        while (stateMachine.hasMoreSteps()) {
            String nextStep = stateMachine.getNextStep();
            result += nextStep;
            stateMachine.completeStep(nextStep);
        }

        System.out.println("Result is " + result);
        System.out.print("Pass? ");
        if (IS_TEST) {
            System.out.println(result.equals("CABDFE"));
        } else {
            System.out.println(result.equals("IJLFUVDACEHGRZPNKQWSBTMXOY"));
        }

        System.out.println();
        /********************************
         * Part 2
         ********************************/
        stateMachine = new StateMachine(MIN_SECONDS);
        stateMachine.build(input);

        result = "";
        int seconds = 0;
        Map<String, Integer> workers = new TreeMap<>();
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers.put(Integer.toString(i), 0);
        }

        while (stateMachine.hasMoreSteps()) {
            List<String> finishedWorkers = new ArrayList<>();
            Map<String, Integer> newWorkers = new HashMap<>();

            List<Map.Entry<String, Integer>> entries = new ArrayList<>(workers.entrySet());
            entries.sort(Map.Entry.comparingByValue());

            for (int i = entries.size() - 1; i >= 0; i--) {
                Map.Entry<String, Integer> worker = entries.get(i);
                if (worker.getValue() == 0) {
                    finishedWorkers.add(worker.getKey());
                    stateMachine.completeStep(worker.getKey());
                    String nextStep = stateMachine.getNextStep();
                    if (nextStep != null) {
                        newWorkers.put(nextStep, stateMachine.getStepCost(nextStep));
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

//            printHeaders();
//            System.out.print(seconds + "\t\t");
//            for (String worker : workers.keySet()) {
//                System.out.print(worker + "\t\t\t");
//            }

            seconds++;
        }

        seconds--;
        System.out.println("Result is " + result);
        System.out.println("Time is " + seconds); //1072
        System.out.println("Pass? ");
        if (IS_TEST) {
            System.out.println(result.equals("CABFDE"));
            System.out.println(seconds == 15);
        } else {
            System.out.println(result.equals("IJLVFDUHACERGZPNQKWSBTMXOY"));
            System.out.println(seconds == 1072);
        }
    }

    private static void printHeaders() {
        System.out.print("Second\t");
        for (int i = 0; i < NUM_WORKERS; i++) {
            System.out.print("Worker " + (i + 1) + "\t");
        }
        System.out.println("Done");
    }

}
