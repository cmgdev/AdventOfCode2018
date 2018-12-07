package day7;

import base.AbstractPuzzle;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachine {

    Map<String, List<String>> stateMachine = new HashMap<>();
    List<String> inProgress = new ArrayList<>();
    int minCostPerStep = 0;

    public StateMachine(int minCostPerStep) {
        this.minCostPerStep = minCostPerStep;
    }

    public void build(List<String> input) {
        for (String line : input) {
            String step = line.substring(5, 6);
            String successor = line.substring(36, 37);
            List<String> successors = stateMachine.getOrDefault(step, new ArrayList<>());
            successors.add(successor);
            stateMachine.put(step, successors);

            if (!stateMachine.containsKey(successor)) {
                stateMachine.put(successor, new ArrayList<>());
            }
        }
    }

    public int getStepCost(String step) {
        return AbstractPuzzle.ALPHABET_UPPER.indexOf(step) + minCostPerStep;
    }

    public String getNextStep() {
        List<String> allSuccessors = stateMachine.values().stream()
                .flatMap(l -> l.stream())
                .distinct()
                .collect(Collectors.toList());
        List<String> allPredecessors = new ArrayList(stateMachine.keySet());

        allPredecessors.removeAll(allSuccessors);
        allPredecessors.removeAll(inProgress);
        Collections.sort(allPredecessors);

        if (!allPredecessors.isEmpty()) {
            String nextStep = allPredecessors.get(0);
            inProgress.add(nextStep);
            return nextStep;
        } else {
            return null;
        }
    }

    public boolean hasMoreSteps() {
        return !stateMachine.isEmpty();
    }

    public void completeStep(String step) {
        inProgress.remove(step);
        stateMachine.remove(step);
    }

}
