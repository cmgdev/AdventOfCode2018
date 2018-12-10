package day07;

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
            List<String> successors = this.stateMachine.getOrDefault(step, new ArrayList<>());
            successors.add(successor);
            this.stateMachine.put(step, successors);

            if (!this.stateMachine.containsKey(successor)) {
                this.stateMachine.put(successor, new ArrayList<>());
            }
        }
    }

    public int getStepCost(String step) {
        return AbstractPuzzle.ALPHABET_UPPER.indexOf(step) + minCostPerStep + 1;
    }

    public String getNextStep() {
        List<String> allSuccessors = this.stateMachine.values().stream()
                .flatMap(l -> l.stream())
                .distinct()
                .collect(Collectors.toList());
        List<String> allPredecessors = new ArrayList(this.stateMachine.keySet());

        allPredecessors.removeAll(allSuccessors);
        allPredecessors.removeAll(this.inProgress);
        Collections.sort(allPredecessors);

        if (!allPredecessors.isEmpty()) {
            String nextStep = allPredecessors.get(0);
            this.inProgress.add(nextStep);
            return nextStep;
        } else {
            return null;
        }
    }

    public boolean hasMoreSteps() {
        return !stateMachine.isEmpty();
    }

    public void completeStep(String step) {
        this.inProgress.remove(step);
        this.stateMachine.remove(step);
    }

}
